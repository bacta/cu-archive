package com.ocdsoft.bacta.swg.cu.message.login.server;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import lombok.Getter;

import java.nio.ByteBuffer;

public class GameServerStatusResponse extends GameNetworkMessage {

    private static final short priority = 0x2;
    private static final int messageType = SOECRC32.hashCode(GameServerStatusResponse.class.getSimpleName());

    @Getter
    private final int clusterId;

	public GameServerStatusResponse(final int clusterId) {
        super(priority, messageType);

        this.clusterId = clusterId;
	}

    public GameServerStatusResponse(ByteBuffer buffer) {
        super(priority, messageType);

        clusterId = buffer.getInt();
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        buffer.putInt(clusterId);
    }
}
