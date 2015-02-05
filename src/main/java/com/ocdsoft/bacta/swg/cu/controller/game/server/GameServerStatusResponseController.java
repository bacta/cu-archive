package com.ocdsoft.bacta.swg.cu.controller.game.server;

import com.google.inject.Inject;
import com.ocdsoft.bacta.engine.network.client.ConnectionState;
import com.ocdsoft.bacta.soe.ServerType;
import com.ocdsoft.bacta.soe.SwgController;
import com.ocdsoft.bacta.soe.SwgMessageController;
import com.ocdsoft.bacta.soe.annotation.RolesAllowed;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.io.udp.game.GameServerState;
import com.ocdsoft.bacta.swg.cu.message.login.server.GameServerStatusResponse;
import com.ocdsoft.bacta.swg.cu.object.login.ClusterEntry;

/**
 * Created by kburkhardt on 1/31/15.
 */
@SwgController(server = ServerType.GAME, handles = GameServerStatusResponse.class)
@RolesAllowed({ConnectionRole.WHITELISTED})
public class GameServerStatusResponseController implements SwgMessageController<GameServerStatusResponse> {
    
    private final GameServerState<ClusterEntry> serverState;
    
    @Inject
    public GameServerStatusResponseController(final GameServerState<ClusterEntry> serverState) {
        this.serverState = serverState;
    }
    
    
    @Override
    public void handleIncoming(SoeUdpConnection loginConnection, GameServerStatusResponse message) throws Exception {

        if(serverState.getId() < 2) {
            serverState.setId(message.getClusterId());
        }
        
        loginConnection.setState(ConnectionState.DISCONNECTED);
    }
}
