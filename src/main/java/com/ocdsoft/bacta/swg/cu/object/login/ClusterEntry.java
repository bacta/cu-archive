package com.ocdsoft.bacta.swg.cu.object.login;

import com.ocdsoft.bacta.engine.buffer.ByteBufferWritable;
import com.ocdsoft.bacta.engine.conf.BactaConfiguration;
import lombok.Getter;

import java.nio.ByteBuffer;
import java.util.Map;

public class ClusterEntry implements ByteBufferWritable, Comparable<ClusterEntry> {

    @Getter
    private final int id;

    @Getter
    private final String secret;

    @Getter
    private final String name;

    @Getter
    private final ClusterStatus clusterStatus;

    @Getter
    private ClusterData clusterData;

    public ClusterEntry(BactaConfiguration configuration) {
        id = configuration.getInt("Bacta/GameServer", "Id");
        secret = configuration.getString("Bacta/GameServer", "Secret");
        name = configuration.getString("Bacta/GameServer", "Name");

        clusterStatus = new ClusterStatus(configuration);
        clusterData = new ClusterData(id, name);
    }

    public ClusterEntry(Map<String, Object> clusterInfo) {

        id = ((Double)clusterInfo.get("id")).intValue();
        secret = (String) clusterInfo.get("secret");
        name = (String) clusterInfo.get("name");

        clusterStatus = new ClusterStatus(clusterInfo);
        clusterData = new ClusterData(id, name);
    }

    @Override
    public int compareTo(ClusterEntry o) {
        return o.getName().compareTo(getName());
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {

    }
}
