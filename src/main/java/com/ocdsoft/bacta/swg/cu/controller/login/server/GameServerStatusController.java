package com.ocdsoft.bacta.swg.cu.controller.login.server;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.ServerType;
import com.ocdsoft.bacta.soe.SwgController;
import com.ocdsoft.bacta.soe.SwgMessageController;
import com.ocdsoft.bacta.soe.annotation.RolesAllowed;
import com.ocdsoft.bacta.soe.connection.ConnectionRole;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.object.ClusterEntryItem;
import com.ocdsoft.bacta.soe.service.ClusterService;
import com.ocdsoft.bacta.swg.cu.message.game.server.GameServerStatus;
import com.ocdsoft.bacta.swg.cu.message.login.server.GameServerStatusResponse;
import com.ocdsoft.bacta.swg.cu.object.login.ClusterEntry;

/**
 * Created by kburkhardt on 1/31/15.
 */
@SwgController(server = ServerType.LOGIN, handles = GameServerStatus.class)
@RolesAllowed({ConnectionRole.WHITELISTED})
public class GameServerStatusController implements SwgMessageController<GameServerStatus> {

    private final ClusterService<ClusterEntry>  clusterService;

    @Inject
    public GameServerStatusController(final ClusterService<ClusterEntry> clusterService) {
        this.clusterService = clusterService;
    }

    @Override
    public void handleIncoming(SoeUdpConnection loginConnection, GameServerStatus message) throws Exception {
        ClusterEntryItem clusterEntryItem = clusterService.updateClusterInfo(message.getClusterEntry());
        
        GameServerStatusResponse gameServerStatusResponse = new GameServerStatusResponse(clusterEntryItem.getId());
        loginConnection.sendMessage(gameServerStatusResponse);
    }
}
