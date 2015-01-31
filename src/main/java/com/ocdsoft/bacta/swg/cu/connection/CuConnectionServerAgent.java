package com.ocdsoft.bacta.swg.cu.connection;

import com.google.inject.Inject;
import com.ocdsoft.bacta.engine.network.client.ConnectionState;
import com.ocdsoft.bacta.soe.client.ClientConnection;
import com.ocdsoft.bacta.soe.connection.ConnectionServerAgent;
import com.ocdsoft.bacta.soe.io.udp.game.GameServerState;
import com.ocdsoft.bacta.soe.service.SessionKeyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;

/**
 * Created by kburkhardt on 1/26/15.
 */

@Singleton
public class CuConnectionServerAgent implements ConnectionServerAgent {

    private static final Logger logger = LoggerFactory.getLogger(CuConnectionServerAgent.class);

    private final GameServerState serverState;
    private final ClientConnection clientConnection;

    private final SessionKeyService sessionKeyService;

    private final Thread agentThread;

    @Inject
    public CuConnectionServerAgent(final SessionKeyService sessionKeyService, final GameServerState serverState, final ClientConnection clientConnection) {
        this.sessionKeyService = sessionKeyService;
        this.serverState = serverState;
        this.clientConnection = clientConnection;

        agentThread = new Thread(clientConnection);
        this.clientConnection.setConnectCallback(this::onConnect);
        this.clientConnection.setConnectCallback(this::onConnect);
    }

    @Override
    public void run() {

        agentThread.start();

        try {
            while (true) {

                // 30 Seconds between status updates
                Thread.sleep(1000 * 30);
                update();
            }

        } catch(InterruptedException e) {
            logger.warn("Updater thread interrupted", e);
        }
    }

    @Override
    public void update() {

        if(clientConnection.getState() != ConnectionState.ONLINE) {
            clientConnection.connect(sessionKeyService.getNextKey());
        } else {
            onConnect(null);
        }
    }

    private Void onConnect(Void aVoid) {



        return null;
    }


}
