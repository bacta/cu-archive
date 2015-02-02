package com.ocdsoft.bacta.swg.cu.message.login.server;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import com.ocdsoft.bacta.swg.cu.object.login.ClusterEntry;
import com.ocdsoft.bacta.swg.cu.object.login.ClusterStatus;

import java.nio.ByteBuffer;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class LoginClusterStatus extends GameNetworkMessage {

    private static final short priority = 0x3;
    private static final int messageType = SOECRC32.hashCode(LoginClusterStatus.class.getSimpleName());

    private final Set<ClusterStatus> clusterStatusSet;

	public LoginClusterStatus(Set<ClusterEntry> clusterEntrySet) {
        super(priority, messageType);

        clusterStatusSet = new TreeSet<>();
        clusterStatusSet.addAll(clusterEntrySet.stream().map(ClusterEntry::getClusterStatus).collect(Collectors.toList()));
	}

    public LoginClusterStatus(ByteBuffer buffer) {
        super(priority, messageType);

        clusterStatusSet = new TreeSet<>();
        int count = buffer.getInt();
        for(int i = 0; i < count; ++i) {
            ClusterStatus status = new ClusterStatus(buffer);
            clusterStatusSet.add(status);
        }
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {

        buffer.putInt(clusterStatusSet.size());

        for (ClusterStatus clusterStatus: clusterStatusSet) {
            clusterStatus.writeToBuffer(buffer);
        }
    }
}
