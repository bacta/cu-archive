package com.ocdsoft.bacta.swg.cu.message.game.server;

import com.ocdsoft.bacta.soe.io.udp.game.GameServerState;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import com.ocdsoft.bacta.swg.cu.object.login.ClusterEntry;
import lombok.Getter;

import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;

public class GameServerStatus extends GameNetworkMessage {

    private static final short priority = 0x4;
    private static final int messageType = SOECRC32.hashCode(GameServerStatus.class.getSimpleName());

//    private MessageDigest md = MessageDigest.getInstance("MD5");

    @Getter
    private final ClusterEntry clusterEntry;

    public GameServerStatus(GameServerState<ClusterEntry> gameServerState) throws NoSuchAlgorithmException {
        super(priority, messageType);

        this.clusterEntry = gameServerState.getClusterEntry();
	}

    public GameServerStatus(ByteBuffer buffer) {
        super(priority, messageType);

        this.clusterEntry = null;
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
//        writeAscii(new String(md.digest(serverState.getSecret().getBytes())));
//        writeAscii(serverState.getName());
//        writeAscii(serverState.getAddress());
//        writeInt(serverState.getPort());
//        writeInt(serverState.getPingPort());
//        writeInt(serverState.getPopulation());
//        writeInt(serverState.getMaximumPopulation());
//        writeInt(serverState.getMaximumCharacters());
//        writeInt(serverState.getTimezone());
//        writeInt(serverState.getConnectionState().getValue());
//        writeBoolean(serverState.isRecommended());
    }
}
