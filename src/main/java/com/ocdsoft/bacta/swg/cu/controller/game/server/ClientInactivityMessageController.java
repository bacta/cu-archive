package com.ocdsoft.bacta.swg.cu.controller.game.server;

import com.ocdsoft.bacta.soe.SwgController;
import com.ocdsoft.bacta.soe.SwgMessageController;
import com.ocdsoft.bacta.soe.annotation.RolesAllowed;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.cu.message.game.client.ClientInactivityMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SwgController(ClientInactivityMessage.class)
@RolesAllowed({ConnectionRole.AUTHENTICATED})
public class ClientInactivityMessageController implements SwgMessageController<ClientInactivityMessage> {

    private static final Logger logger = LoggerFactory.getLogger(ClientInactivityMessageController.class.getSimpleName());

    @Override
    public void handleIncoming(SoeUdpConnection connection, ClientInactivityMessage message) throws Exception {
        logger.warn("This controller is not implemented");
    }
}

