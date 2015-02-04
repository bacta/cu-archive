package com.ocdsoft.bacta.swg.cu.object;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.engine.conf.BactaConfiguration;
import com.ocdsoft.bacta.engine.data.GameDatabaseConnector;
import com.ocdsoft.bacta.engine.service.object.ObjectService;
import com.ocdsoft.bacta.engine.service.objectfactory.NetworkObjectFactory;
import com.ocdsoft.bacta.swg.cu.object.game.SceneObject;
import com.ocdsoft.bacta.swg.data.ObjectTemplateService;
import com.ocdsoft.bacta.swg.shared.object.template.ObjectTemplate;
import gnu.trove.map.TLongObjectMap;
import gnu.trove.map.hash.TLongObjectHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Kyle on 3/24/14.
 */
@Singleton
public class SceneObjectService implements ObjectService<SceneObject> {

    private final static Logger logger = LoggerFactory.getLogger(SceneObjectService.class);

    private TLongObjectMap<SceneObject> internalMap = new TLongObjectHashMap<>();

//    private Set<SceneObject> dirtyList = Collections.newSetFromMap(new ConcurrentHashMap<SceneObject, Boolean>());
//    private OnDirtyCallbackBase onDirtyCallback = new SceneObjectServiceOnDirtyCallback();

    private final NetworkObjectFactory networkObjectFactory;
    private final int deltaUpdateInterval;
//    private final DeltaNetworkDispatcher deltaDispatcher;
    private final GameDatabaseConnector gameDatabaseConnector;
    private final ObjectTemplateService<SceneObject> objectTemplateService;

//    @Inject
//    private ContainerService containerService;

    @Inject
    public SceneObjectService(BactaConfiguration configuration,
                              NetworkObjectFactory networkObjectFactory,
                              GameDatabaseConnector gameDatabaseConnector,
                              ObjectTemplateService<SceneObject> objectTemplateService) {

        this.networkObjectFactory = networkObjectFactory;
        deltaUpdateInterval = configuration.getIntWithDefault("Bacta/GameServer", "DeltaUpdateInterval", 50);
        this.gameDatabaseConnector = gameDatabaseConnector;
        this.objectTemplateService = objectTemplateService;
//        deltaDispatcher = new DeltaNetworkDispatcher();
//        new Thread(deltaDispatcher).start();
    }

    @Override
    public <T extends SceneObject> T createObject(long creator, String templatePath) {

        ObjectTemplate template = objectTemplateService.getObjectTemplate(templatePath);
        Class<? extends SceneObject> objectClass = objectTemplateService.getClassForTemplate(template);

        T newObject = (T) networkObjectFactory.createNetworkObject(objectClass);
        newObject.setObjectTemplate(template);
//        newObject.setOnDirtyCallback(onDirtyCallback);

        loadTemplateData(creator, newObject);

        internalMap.put(newObject.getNetworkId(), newObject);
        //gameDatabaseConnector.createNetworkObject(newObject);

        return newObject;
    }

    private <T extends SceneObject> void loadTemplateData(long creator, T newObject) {
        ObjectTemplate template = newObject.getObjectTemplate();
//        containerService.createObjectContainer(newObject);
    }

    @Override
    public <T extends SceneObject> T get(long key) {
        T object = (T) internalMap.get(key);

        if(object == null) {
            object = gameDatabaseConnector.getNetworkObject(key);
            if(object != null) {
                //ObjectTemplate template = objectTemplateService.getObjectTemplate(object.getTemplatePath());
                //object.setTemplate(template); //TODO: Fix this!!!!!!
                internalMap.put(key, object);
            }
        }

        return (T) object;
    }

    @Override
    public <T extends SceneObject> T get(SceneObject requester, long key) {
        //TODO: Reimplement permissions.
        return get(key);
    }

    @Override
    public <T extends SceneObject> void updateObject(T object) {
        //gameDatabaseConnector.updateNetworkObject(object);
    }


    // Executor?
    private class DeltaNetworkDispatcher implements Runnable {

        protected final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

        @Override
        public void run() {

//            long nextIteration = 0;
//
//            while(true) {
//                try {
//                    long currentTime = System.currentTimeMillis();
//
//                    if (nextIteration > currentTime) {
//                        Thread.sleep(nextIteration - currentTime);
//                    }
//
//                    for (SceneObject object : dirtyList) {
//                        if (object.isInitialized())
//                            object.sendDeltas();
//
//                        object.clearDeltas();
//                    }
//
//                    dirtyList.clear();
//
//                    nextIteration = currentTime + deltaUpdateInterval;
//
//                } catch(Exception e) {
//                    logger.error("Unknown", e);
//                }
//            }
        }
    }

//    private class SceneObjectServiceOnDirtyCallback implements OnDirtyCallbackBase {
//
//        @Override
//        public void onDirty(SceneObject sceneObject) {
//            dirtyList.add(sceneObject);
//        }
//
//    }
}
