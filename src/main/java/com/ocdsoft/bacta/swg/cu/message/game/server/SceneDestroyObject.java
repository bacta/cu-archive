package com.ocdsoft.bacta.swg.cu.message.game.server;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import com.ocdsoft.bacta.swg.cu.object.game.SceneObject;

import java.nio.ByteBuffer;

/**
struct __cppobj SceneDestroyObject : GameNetworkMessage
{
    Archive::AutoVariable<NetworkId> m_networkId;
    Archive::AutoVariable<bool> m_hyperspace;
};
 */
public class SceneDestroyObject extends GameNetworkMessage {

    private static final short priority = 0x3;
    private static final int messageType = SOECRC32.hashCode(SceneDestroyObject.class.getSimpleName());
    
    private final long networkId;
    private final boolean hyperspace;
    
    public SceneDestroyObject(SceneObject scno) {
		super(priority, messageType);

        networkId = scno.getNetworkId();
        hyperspace = scno.isHyperspace();
	}

    public SceneDestroyObject(ByteBuffer buffer) {
        super(priority, messageType);

        networkId = buffer.getLong();
        hyperspace = BufferUtil.getBoolean(buffer);
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        buffer.putLong(networkId);
        BufferUtil.putBoolean(buffer, hyperspace);
    }
}
