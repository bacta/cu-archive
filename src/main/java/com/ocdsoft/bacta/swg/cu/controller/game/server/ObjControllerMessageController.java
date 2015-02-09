package com.ocdsoft.bacta.swg.cu.controller.game.server;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.ocdsoft.bacta.engine.service.object.ObjectService;
import com.ocdsoft.bacta.soe.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.GameNetworkMessageHandled;
import com.ocdsoft.bacta.soe.RolesAllowed;
import com.ocdsoft.bacta.soe.ServerType;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.util.SoeMessageUtil;
import com.ocdsoft.bacta.swg.cu.message.game.client.ObjControllerMessage;
import com.ocdsoft.bacta.swg.cu.object.SceneObject;
import com.ocdsoft.bacta.swg.cu.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.object.ObjController;
import com.ocdsoft.bacta.swg.object.ObjControllerHandled;
import com.ocdsoft.bacta.swg.object.ObjControllerId;
import com.ocdsoft.bacta.swg.object.ObjectController;
import com.ocdsoft.bacta.swg.shared.util.ObjectControllerNames;
import com.ocdsoft.bacta.swg.util.ObjMessageTemplateWriter;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.Getter;
import org.apache.velocity.app.VelocityEngine;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.Set;

@GameNetworkMessageHandled(ObjControllerMessage.class)
@RolesAllowed({ConnectionRole.AUTHENTICATED})
public class ObjControllerMessageController implements GameNetworkMessageController<ObjControllerMessage> {

    private static final Logger logger = LoggerFactory.getLogger(ObjControllerMessageController.class.getSimpleName());

    private VelocityEngine ve = null;
    
    private final TIntObjectMap<ControllerData> controllers;

    private final ObjectService<SceneObject> objectService;

    private final Injector injector;
    
    private final ObjMessageTemplateWriter objMessageTemplateWriter;

    @Inject
    public ObjControllerMessageController(final Injector injector, final ObjectService<SceneObject> objectService) {
        this.controllers = new TIntObjectHashMap<>();
        this.objectService = objectService;
        this.injector = injector;
        objMessageTemplateWriter = new ObjMessageTemplateWriter(ServerType.GAME);
        loadControllers();
    }
    
    @Override
    public void handleIncoming(SoeUdpConnection connection, ObjControllerMessage objControllerMessage) throws Exception {
        
        int flags = objControllerMessage.getFlags();
        TangibleObject invoker = objectService.get(objControllerMessage.getNetworkId());
        
        ControllerData controllerData = controllers.get(objControllerMessage.getMessage());
        if(controllerData != null) {
            
            ObjController controller = controllerData.getObjController();
            Constructor<? extends ObjectController> constructor = controllerData.getConstructor();
            
            ObjectController message = constructor.newInstance(objControllerMessage.getBuffer());
            
            logger.trace("Routing to " + controller.getClass().getSimpleName());
            controller.handleIncoming(connection, message, invoker);
        
        } else {
            
            handleMissingController(objControllerMessage.getMessage(), objControllerMessage.getBuffer());

            logger.error("Unhandled ObjectController: '" + ObjectControllerNames.get(objControllerMessage.getMessage()) + "' 0x" + Integer.toHexString(objControllerMessage.getMessage()));
            logger.error(SoeMessageUtil.bytesToHex(objControllerMessage.getBuffer()));
        }
    }

    private void handleMissingController(int opcode, ByteBuffer message) {

        objMessageTemplateWriter.createFiles(opcode, message, TangibleObject.class.getName(), ObjectController.class.getName());

        logger.error("Unhandled ObjectController: '" + ObjectControllerNames.get(opcode) + "' 0x" + Integer.toHexString(opcode));
        logger.error(SoeMessageUtil.bytesToHex(message));
    }

    private void loadControllers() {

        String projectClassPath = System.getProperty("base.classpath");
        Reflections reflections = new Reflections(projectClassPath + ".controller.game.object");

        Set<Class<? extends ObjController>> subTypes = reflections.getSubTypesOf(ObjController.class);

        Iterator<Class<? extends ObjController>> iter = subTypes.iterator();

        while (iter.hasNext()) {


            try {
                Class<? extends ObjController> controllerClass = iter.next();

                ObjControllerHandled controllerAnnotation = controllerClass.getAnnotation(ObjControllerHandled.class);

                if (controllerAnnotation == null) {
                    logger.info("Missing @ObjControllerHandled annotation, discarding: " + controllerClass.getName());
                    continue;
                }

                Class<?> handledMessage = controllerAnnotation.value();
                ObjControllerId objControllerId =  handledMessage.getAnnotation(ObjControllerId.class);

                ObjController controller = injector.getInstance(controllerClass);
                ControllerData controllerData = new ControllerData(controller, handledMessage.getConstructor(ByteBuffer.class));

                if (!controllers.containsKey(objControllerId.value())) {
					logger.debug("Adding object controller: " + ObjectControllerNames.get(objControllerId.value()));
                    controllers.put(objControllerId.value(), controllerData);
                } else {
                    logger.warn("Controller already exists for '0x{}' Attempted to load {}", Integer.toHexString(objControllerId.value()), controller.getClass().getName());
                }
                
            } catch (Exception e) {
                logger.error("Unable to add controller", e);
            }
        }
    }

    private class ControllerData {
        @Getter
        private final ObjController objController;

        @Getter
        private final Constructor constructor;

        public ControllerData(final ObjController objController,
                              final Constructor constructor) {
            this.objController = objController;
            this.constructor = constructor;

        }

    }
}

