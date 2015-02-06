package com.ocdsoft.bacta.swg.cu.controller.game.server;

import com.ocdsoft.bacta.soe.RolesAllowed;
import com.ocdsoft.bacta.soe.SwgController;
import com.ocdsoft.bacta.soe.SwgMessageController;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.cu.message.game.client.NewTicketActivityMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SwgController(NewTicketActivityMessage.class)
@RolesAllowed({ConnectionRole.AUTHENTICATED})
public class NewTicketActivityMessageController implements SwgMessageController<NewTicketActivityMessage> {

    private static final Logger logger = LoggerFactory.getLogger(NewTicketActivityMessageController.class.getSimpleName());

    @Override
    public void handleIncoming(SoeUdpConnection connection, NewTicketActivityMessage message) throws Exception {
        logger.warn("This controller is not implemented");
    }
}

