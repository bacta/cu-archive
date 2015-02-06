package com.ocdsoft.bacta.swg.cu.controller.game.server;

import com.ocdsoft.bacta.soe.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.GameNetworkMessageHandled;
import com.ocdsoft.bacta.soe.RolesAllowed;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.cu.message.game.client.LagRequest;
import com.ocdsoft.bacta.swg.cu.message.game.server.ConnectionServerLagResponse;
import com.ocdsoft.bacta.swg.cu.message.game.server.GameServerLagResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GameNetworkMessageHandled(LagRequest.class)
@RolesAllowed({})
public class LagRequestController implements GameNetworkMessageController<LagRequest> {

    private static final Logger logger = LoggerFactory.getLogger(LagRequestController.class.getSimpleName());

    @Override
    public void handleIncoming(SoeUdpConnection connection, LagRequest message) throws Exception {
        connection.sendMessage(new ConnectionServerLagResponse());
        connection.sendMessage(new GameServerLagResponse());
    }
}

