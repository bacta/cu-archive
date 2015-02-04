package com.ocdsoft.bacta.swg.cu.controller.game.server;

import com.google.inject.Inject;
import com.ocdsoft.bacta.engine.service.object.ObjectService;
import com.ocdsoft.bacta.soe.ServerType;
import com.ocdsoft.bacta.soe.SwgController;
import com.ocdsoft.bacta.soe.SwgMessageController;
import com.ocdsoft.bacta.soe.annotation.RolesAllowed;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.io.udp.game.GameServerState;
import com.ocdsoft.bacta.swg.cu.message.game.client.SelectCharacter;
import com.ocdsoft.bacta.swg.cu.message.game.server.CmdStartScene;
import com.ocdsoft.bacta.swg.cu.message.game.server.ParametersMessage;
import com.ocdsoft.bacta.swg.cu.message.game.server.UnkByteFlag;
import com.ocdsoft.bacta.swg.cu.object.game.SceneObject;
import com.ocdsoft.bacta.swg.cu.object.game.tangible.creature.CreatureObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SwgController(server=ServerType.GAME, handles=SelectCharacter.class)
@RolesAllowed({ConnectionRole.AUTHENTICATED})
public class SelectCharacterController implements SwgMessageController<SelectCharacter> {

    private static final Logger logger = LoggerFactory.getLogger(SelectCharacterController.class.getSimpleName());

    @Inject
    private final ObjectService<SceneObject> objectService;

    @Inject
    private final GameServerState serverState;

    @Inject
    public SelectCharacterController(final ObjectService<SceneObject> objectService,
                                     final GameServerState serverState) {
        
        this.objectService = objectService;
        this.serverState = serverState;
    }
    
//    @Inject
//    private ZoneMap zoneMap;
    
    @Override
    public void handleIncoming(SoeUdpConnection connection, SelectCharacter message) throws Exception {

        CreatureObject character = objectService.get(message.getNetworkId());
        
        // Todo: Check account ownership
        if (character != null) {

//            connection.setCharacter(character);
//            character.setClient(connection);

            UnkByteFlag flag = new UnkByteFlag((byte) 1);
            connection.sendMessage(flag);

//            Zone zone = zoneMap.get("tatooine");
            //TODO: Lots of variables to send
            boolean disableWorldSnapshot = false;
            float startYaw = 0;
            long timeSeconds = 1000;
            int serverEpoch = 0;
            
            CmdStartScene start = new CmdStartScene(
                    disableWorldSnapshot,
                    character.getNetworkId(),
                    "terrain/tatooine.trn",
                    character.getTransform().getPosition(),
                    startYaw,
                    character.getObjectTemplate().getName(),
                    timeSeconds,
                    serverEpoch);
            connection.sendMessage(start);

            // TODO: add weatherUpdateInterval
            int weatherUpdateInterval = 1000;
            ParametersMessage parammessage = new ParametersMessage(weatherUpdateInterval);
            connection.sendMessage(parammessage);

//            connection.sendMessage(new ChatOnConnectAvatar());

//			GuildObject guildObject = new GuildObject(); //TODO one global GuildObject list for server.
//			guildObject.setClientCRC(0x7D40E2E6);
//			guildObject.setNetworkId(400);
//
//			guildObject.sendTo(character);

//            character.sendTo(client);
//            zone.add(character);

        } else {
            logger.error("Unable to lookup character oid: " + message.getNetworkId());
        }    }
}

