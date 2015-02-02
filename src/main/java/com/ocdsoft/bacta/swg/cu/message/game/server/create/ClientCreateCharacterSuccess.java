package com.ocdsoft.bacta.swg.cu.message.game.server.create;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;

import java.nio.ByteBuffer;

/**
 struct __cppobj ClientCreateCharacterSuccess : GameNetworkMessage
 {
    Archive::AutoVariable<NetworkId> m_networkId;
 }; 
 */
public class ClientCreateCharacterSuccess extends GameNetworkMessage {

    private static final short priority = 0x2;
    private static final int messageType = SOECRC32.hashCode(ClientCreateCharacterSuccess.class.getSimpleName());

    private final long networkId;

    public ClientCreateCharacterSuccess(final long networkId) {
		super(priority, messageType);
		
		this.networkId = networkId;
	}

    public ClientCreateCharacterSuccess(final ByteBuffer buffer) {
        super(priority, messageType);

        this.networkId = buffer.getLong();
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        buffer.putLong(networkId);
    }
}
