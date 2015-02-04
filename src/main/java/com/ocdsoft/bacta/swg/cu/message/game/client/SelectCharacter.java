package com.ocdsoft.bacta.swg.cu.message.game.client;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import lombok.Getter;

import java.nio.ByteBuffer;


/**
 struct __cppobj SelectCharacter : GameNetworkMessage
 {
    Archive::AutoVariable<NetworkId> m_id;
 };
 */
public class SelectCharacter extends GameNetworkMessage {

    private static final short priority = 0x9;
    private static final int messageType = SOECRC32.hashCode(SelectCharacter.class.getSimpleName());

    @Getter
    private final long networkId;

    public SelectCharacter(final long networkId) {
        super(priority, messageType);
        
        this.networkId = networkId;
    }

    public SelectCharacter(final ByteBuffer buffer) {
        super(priority, messageType);
    
        this.networkId = buffer.getLong();
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        buffer.putLong(networkId);
    }
    /**
         00 09 00 04 02 00 76 8D 09 B5 05 00 00 00 01 00
    00 00 

     */
}
