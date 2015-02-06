package com.ocdsoft.bacta.swg.cu.controller.game.server;

import com.ocdsoft.bacta.soe.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.GameNetworkMessageHandled;
import com.ocdsoft.bacta.soe.RolesAllowed;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.cu.message.game.client.NewbieTutorialResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GameNetworkMessageHandled(NewbieTutorialResponse.class)
@RolesAllowed({ConnectionRole.AUTHENTICATED})
public class NewbieTutorialResponseController implements GameNetworkMessageController<NewbieTutorialResponse> {

    private static final Logger logger = LoggerFactory.getLogger(NewbieTutorialResponseController.class.getSimpleName());

    @Override
    public void handleIncoming(SoeUdpConnection connection, NewbieTutorialResponse message) throws Exception {
        logger.warn("This controller is not implemented");
    }
}

