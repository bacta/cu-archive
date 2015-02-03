package com.ocdsoft.bacta.swg.cu.message.game.server.scene;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import com.ocdsoft.bacta.swg.cu.object.game.SceneObject;

import java.nio.ByteBuffer;

public class SceneEndBaselines extends GameNetworkMessage {

    private static final short priority = 0x2;
    private static final int messageType = SOECRC32.hashCode(SceneEndBaselines.class.getSimpleName());

    private final long networkId;
    
    public SceneEndBaselines(SceneObject scno) {
		super(priority, messageType);
        
        this.networkId = scno.getNetworkId();
	}

	public SceneEndBaselines(long networkId) {
        super(priority, messageType);  // CRC

        this.networkId = networkId;
	}

    public SceneEndBaselines(ByteBuffer buffer) {
        super(priority, messageType);  // CRC

        this.networkId = buffer.getLong();
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        buffer.putLong(networkId);
    }
}
