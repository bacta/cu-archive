package com.ocdsoft.bacta.swg.cu.controller.chat;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.RolesAllowed;
import com.ocdsoft.bacta.soe.SwgController;
import com.ocdsoft.bacta.soe.SwgMessageController;
import com.ocdsoft.bacta.soe.chat.ChatServer;
import com.ocdsoft.bacta.soe.chat.message.ChatAgentIdentity;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 2/1/15.
 */
@SwgController(ChatAgentIdentity.class)
@RolesAllowed({ConnectionRole.WHITELISTED})
public class ChatAgentIdentityController implements SwgMessageController<ChatAgentIdentity> {
    private static final Logger logger = LoggerFactory.getLogger(ChatAgentIdentityController.class);

    private final ChatServer chatServer;

    @Inject
    public ChatAgentIdentityController(final ChatServer chatServer) {
        this.chatServer = chatServer;
    }

    @Override
    public void handleIncoming(SoeUdpConnection loginConnection, ChatAgentIdentity message) throws Exception {
        logger.info(String.format("%s (%s:%d)", message.getName(), message.getAddress(), message.getPort()));
    }
}