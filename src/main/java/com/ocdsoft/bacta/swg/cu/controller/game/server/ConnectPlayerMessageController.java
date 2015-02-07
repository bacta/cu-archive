package com.ocdsoft.bacta.swg.cu.controller.game.server;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.GameNetworkMessageHandled;
import com.ocdsoft.bacta.soe.RolesAllowed;
import com.ocdsoft.bacta.soe.chat.ChatServerAgentService;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.cu.message.game.client.ConnectPlayerMessage;
import com.ocdsoft.bacta.swg.cu.message.game.server.ConnectPlayerResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GameNetworkMessageHandled(ConnectPlayerMessage.class)
@RolesAllowed({ConnectionRole.AUTHENTICATED})
public class ConnectPlayerMessageController implements GameNetworkMessageController<ConnectPlayerMessage> {

    private static final Logger logger = LoggerFactory.getLogger(ConnectPlayerMessageController.class.getSimpleName());

    private final ChatServerAgentService chatService;

    @Inject
    public ConnectPlayerMessageController(final ChatServerAgentService chatService) {
        this.chatService = chatService;
    }

    @Override
    public void handleIncoming(SoeUdpConnection connection, ConnectPlayerMessage message) throws Exception {
        logger.warn("This controller is not completely implemented");
        
        connection.sendMessage(new ConnectPlayerResponseMessage(0));
    }
}

