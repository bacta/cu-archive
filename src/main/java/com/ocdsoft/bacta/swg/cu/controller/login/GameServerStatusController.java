package com.ocdsoft.bacta.swg.cu.controller.login;

import com.google.inject.Inject;
import com.ocdsoft.bacta.soe.ServerType;
import com.ocdsoft.bacta.soe.SwgController;
import com.ocdsoft.bacta.soe.SwgMessageController;
import com.ocdsoft.bacta.soe.annotation.RolesAllowed;
import com.ocdsoft.bacta.soe.connection.ConnectionRoles;
import com.ocdsoft.bacta.soe.io.udp.login.LoginConnection;
import com.ocdsoft.bacta.soe.service.ClusterService;
import com.ocdsoft.bacta.swg.cu.message.game.server.GameServerStatus;
import com.ocdsoft.bacta.swg.cu.object.login.ClusterEntry;

/**
 * Created by kburkhardt on 1/31/15.
 */
@SwgController(server = ServerType.LOGIN, handles = GameServerStatus.class)
@RolesAllowed({ConnectionRoles.WHITELISTED})
public class GameServerStatusController implements SwgMessageController<LoginConnection, GameServerStatus> {

    private final ClusterService<ClusterEntry>  clusterService;

    @Inject
    public GameServerStatusController(final ClusterService<ClusterEntry> clusterService) {
        this.clusterService = clusterService;
    }

    @Override
    public void handleIncoming(LoginConnection loginConnection, GameServerStatus message) throws Exception {
        clusterService.updateClusterInfo(message.getClusterEntry());
    }
}
