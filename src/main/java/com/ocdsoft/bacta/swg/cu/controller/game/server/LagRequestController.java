package com.ocdsoft.bacta.swg.cu.controller.game.server;

import com.ocdsoft.bacta.soe.ServerType;
import com.ocdsoft.bacta.soe.SwgController;
import com.ocdsoft.bacta.soe.SwgMessageController;
import com.ocdsoft.bacta.soe.annotation.RolesAllowed;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.cu.message.game.client.LagRequest;
import com.ocdsoft.bacta.swg.cu.message.game.server.zone.ConnectionServerLagResponse;
import com.ocdsoft.bacta.swg.cu.message.game.server.zone.GameServerLagResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SwgController(server=ServerType.GAME, handles=LagRequest.class)
@RolesAllowed({ConnectionRole.AUTHENTICATED})
public class LagRequestController implements SwgMessageController<LagRequest> {

    private static Logger logger = LoggerFactory.getLogger(LagRequestController.class.getSimpleName());

    @Override
    public void handleIncoming(SoeUdpConnection connection, LagRequest message) throws Exception {
        connection.sendMessage(new ConnectionServerLagResponse());
        connection.sendMessage(new GameServerLagResponse());
    }
}

