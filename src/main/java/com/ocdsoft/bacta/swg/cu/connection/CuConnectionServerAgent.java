package com.ocdsoft.bacta.swg.cu.connection;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.client.ClientConnection;
import com.ocdsoft.bacta.soe.connection.ConnectionServerAgent;
import com.ocdsoft.bacta.soe.io.udp.game.GameServerState;
import com.ocdsoft.bacta.soe.message.ConnectMessage;
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

    @Inject
    public CuConnectionServerAgent(final GameServerState serverState, final ClientConnection clientConnection) {
        this.serverState = serverState;
        this.clientConnection = clientConnection;

        this.clientConnection.setConnectCallback(this::onConnect);
    }

    @Override
    public void run() {

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
        ConnectMessage connectMessage = new ConnectMessage(2, 2, 2);
        clientConnection.sendMessage(connectMessage);
    }

    private Void onConnect(Void aVoid) {

        return null;
    }


}
