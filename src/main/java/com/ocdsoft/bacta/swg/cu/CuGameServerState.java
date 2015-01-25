package com.ocdsoft.bacta.swg.cu;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.engine.conf.BactaConfiguration;
import com.ocdsoft.bacta.engine.network.client.ServerStatus;
import com.ocdsoft.bacta.soe.ServerType;
import com.ocdsoft.bacta.soe.io.udp.game.GameServerState;
import com.ocdsoft.bacta.swg.cu.object.login.ClusterData;
import com.ocdsoft.bacta.swg.cu.object.login.ClusterEntry;
import com.ocdsoft.bacta.swg.cu.object.login.ClusterStatus;

/**
 * Created by Kyle on 3/22/14.
 */
@Singleton
public final class CuGameServerState implements GameServerState<ClusterData, ClusterStatus>, Comparable<CuGameServerState> {

    private final ServerType serverType;
    private ServerStatus serverStatus;
    private final ClusterEntry entry;

    @Inject
    public CuGameServerState(BactaConfiguration configuration) {
        serverType = ServerType.GAME;
        serverStatus = ServerStatus.DOWN;

        entry = new ClusterEntry(configuration);
    }

    public ClusterStatus getClusterStatus() {
        return this.entry.getClusterStatus();
    }

    public int getId() {
        return this.entry.getId();
    }

    public int compareTo(ClusterEntry o) {
        return this.entry.compareTo(o);
    }

    public ClusterData getClusterData() {
        return this.entry.getClusterData();
    }

    public String getSecret() {
        return this.entry.getSecret();
    }

    public String getName() {
        return this.entry.getName();
    }

    @Override
    public int compareTo(CuGameServerState o) {
        return this.getName().compareTo(o.getName());
    }

    @Override
    public ServerType getServerType() {
        return serverType;
    }

    @Override
    public ServerStatus getServerStatus() {
        return serverStatus;
    }

    @Override
    public void setServerStatus(ServerStatus status) {
        this.serverStatus = status;
    }
}
