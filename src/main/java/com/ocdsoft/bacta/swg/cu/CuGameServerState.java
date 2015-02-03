package com.ocdsoft.bacta.swg.cu;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.engine.conf.BactaConfiguration;
import com.ocdsoft.bacta.engine.network.client.ServerStatus;
import com.ocdsoft.bacta.soe.ServerType;
import com.ocdsoft.bacta.soe.io.udp.game.GameServerState;
import com.ocdsoft.bacta.swg.cu.object.login.ClusterEntry;

/**
 * Created by Kyle on 3/22/14.
 */
@Singleton
public final class CuGameServerState implements GameServerState<ClusterEntry>, Comparable<CuGameServerState> {
    private final ClusterEntry entry;

    @Inject
    public CuGameServerState(BactaConfiguration configuration) {
        entry = new ClusterEntry(configuration);
    }

    @Override
    public int compareTo(CuGameServerState o) {
        return this.entry.compareTo(o.getClusterEntry());
    }

    @Override
    public ServerType getServerType() {
        return ServerType.GAME;
    }

    @Override
    public ServerStatus getServerStatus() {
        return entry.getClusterStatus().getStatus();
    }

    @Override
    public void setServerStatus(ServerStatus status) {
        entry.getClusterStatus().setStatus(status);
    }

    @Override
    public ClusterEntry getClusterEntry() {
        return entry;
    }
}
