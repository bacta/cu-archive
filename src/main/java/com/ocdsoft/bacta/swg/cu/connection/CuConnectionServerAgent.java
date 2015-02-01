package com.ocdsoft.bacta.swg.cu.connection;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.ocdsoft.bacta.engine.conf.BactaConfiguration;
import com.ocdsoft.bacta.engine.network.client.ConnectionState;
import com.ocdsoft.bacta.soe.client.ClientConnection;
import com.ocdsoft.bacta.soe.connection.ConnectionServerAgent;
import com.ocdsoft.bacta.soe.io.udp.game.GameServerState;
import com.ocdsoft.bacta.soe.router.SoeMessageRouter;
import com.ocdsoft.bacta.soe.service.SessionKeyService;
import com.ocdsoft.bacta.swg.cu.message.game.server.GameServerStatus;
import com.ocdsoft.bacta.swg.cu.object.login.ClusterEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.net.InetSocketAddress;

/**
 * Created by kburkhardt on 1/26/15.
 */

@Singleton
public class CuConnectionServerAgent implements ConnectionServerAgent {

    private static final Logger logger = LoggerFactory.getLogger(CuConnectionServerAgent.class);

    private final GameServerState<ClusterEntry> serverState;
    private final ClientConnection clientConnection;

    private final SessionKeyService sessionKeyService;

    private final Thread agentThread;

    @Inject
    public CuConnectionServerAgent(final Injector injector,
                                   final SessionKeyService sessionKeyService,
                                   final GameServerState<ClusterEntry> serverState,
                                   final BactaConfiguration configuration) {
        
        this.sessionKeyService = sessionKeyService;
        this.serverState = serverState;

        final SoeMessageRouter soeMessageRouter = new SoeMessageRouter(
                injector,
                configuration.getStringWithDefault("Bacta/GameServer/Client", "SoeControllerList", "clientsoecontrollers.lst"),
                configuration.getStringWithDefault("Bacta/GameServer/Client", "SwgControllerList", "clientswgcontrollers.lst")
        );

        int udpSize = configuration.getIntWithDefault("Bacta/Network", "UdpMaxSize", 496);
        int protocolVersion = configuration.getIntWithDefault("Bacta/Network", "ProtocolVersion", 2);
        String address = configuration.getStringWithDefault("Bacta/LoginServer", "BindIp", "127.0.0.1");
        int port = configuration.getIntWithDefault("Bacta/LoginServer", "Port", 44463);
        
        this.clientConnection = new ClientConnection(soeMessageRouter, udpSize, protocolVersion);

        this.clientConnection.setRemoteAddress(new InetSocketAddress(address, port));
        this.clientConnection.setConnectCallback(this::onConnect);

        agentThread = new Thread(clientConnection);
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

        clientConnection.connect(sessionKeyService.getNextKey());

    }

    private Void onConnect(Void aVoid) {

        GameServerStatus gameServerStatus = new GameServerStatus(serverState);
        clientConnection.sendMessage(gameServerStatus);

        clientConnection.setState(ConnectionState.DISCONNECTED);

        return null;
    }


}
