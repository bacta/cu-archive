package com.ocdsoft.bacta.swg.cu.controller.game.server;

import com.ocdsoft.bacta.soe.RolesAllowed;
import com.ocdsoft.bacta.soe.SwgController;
import com.ocdsoft.bacta.soe.SwgMessageController;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.cu.message.game.client.ConnectPlayerMessage;
import com.ocdsoft.bacta.swg.cu.message.game.server.ConnectPlayerResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SwgController(ConnectPlayerMessage.class)
@RolesAllowed({ConnectionRole.AUTHENTICATED})
public class ConnectPlayerMessageController implements SwgMessageController<ConnectPlayerMessage> {

    private static final Logger logger = LoggerFactory.getLogger(ConnectPlayerMessageController.class.getSimpleName());

    @Override
    public void handleIncoming(SoeUdpConnection connection, ConnectPlayerMessage message) throws Exception {
        logger.warn("This controller is not completely implemented");
        
        connection.sendMessage(new ConnectPlayerResponseMessage(0));
    }
}

