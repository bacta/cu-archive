package com.ocdsoft.bacta.swg.cu.connection;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.ocdsoft.bacta.engine.conf.BactaConfiguration;
import com.ocdsoft.bacta.soe.connection.ConnectionServerAgent;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.io.udp.SoeTransceiver;
import com.ocdsoft.bacta.soe.io.udp.game.GameServerState;
import com.ocdsoft.bacta.soe.service.SessionKeyService;
import com.ocdsoft.bacta.swg.cu.message.game.server.GameServerStatus;
import com.ocdsoft.bacta.swg.cu.object.login.ClusterEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * Created by kburkhardt on 1/26/15.
 */

@Singleton
public class CuConnectionServerAgent implements ConnectionServerAgent {

    private static final Logger logger = LoggerFactory.getLogger(CuConnectionServerAgent.class);

    private final GameServerState<ClusterEntry> serverState;
    private final SoeUdpConnection clientConnection;
    private final int udpSize;
    private final int protocolVersion;

    private final SessionKeyService sessionKeyService;

    private SoeTransceiver transceiver;

    @Inject
    public CuConnectionServerAgent(final Injector injector,
                                   final SessionKeyService sessionKeyService,
                                   final GameServerState<ClusterEntry> serverState,
                                   final BactaConfiguration configuration) throws UnknownHostException {
        
        this.sessionKeyService = sessionKeyService;
        this.serverState = serverState;

        udpSize = configuration.getIntWithDefault("Bacta/Network", "UdpMaxSize", 496);
        protocolVersion = configuration.getIntWithDefault("Bacta/Network", "ProtocolVersion", 2);
        String address = configuration.getStringWithDefault("Bacta/LoginServer", "BindIp", "127.0.0.1");
        int port = configuration.getIntWithDefault("Bacta/LoginServer", "Port", 44463);
        
        this.clientConnection = new SoeUdpConnection();
        this.clientConnection.setRemoteAddress(new InetSocketAddress(address, port));
        this.clientConnection.setConnectCallback(this::onConnect);
    }

    @Override
    public void setTransceiver(final SoeTransceiver transceiver) {
        this.transceiver = transceiver;
    }

    @Override
    public void run() {

        if(transceiver == null) {
            throw new RuntimeException("Unable to run connection agent when transceiver has not been set");
        }

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
        transceiver.createOutgoingConnection(clientConnection);
        clientConnection.connect(protocolVersion, sessionKeyService.getNextKey(), udpSize);
    }
    

    private void onConnect(SoeUdpConnection connection) {

        GameServerStatus gameServerStatus = new GameServerStatus(serverState);
        clientConnection.sendMessage(gameServerStatus);
    }
}
