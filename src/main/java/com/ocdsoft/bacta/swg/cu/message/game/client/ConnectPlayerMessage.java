package com.ocdsoft.bacta.swg.cu.message.game.client;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;

import java.nio.ByteBuffer;

/**
 struct __cppobj ConnectPlayerMessage : GameNetworkMessage
 {
    Archive::AutoVariable<unsigned int> m_stationId;
 }; 
 */
public class ConnectPlayerMessage extends GameNetworkMessage {

    private static final short priority = 0x2;
    private static final int messageType = SOECRC32.hashCode(ConnectPlayerMessage.class.getSimpleName());
    
    private final int stationId;

    public ConnectPlayerMessage(final int stationId) {
        super(priority, messageType);
        
        this.stationId = stationId;
    }

    public ConnectPlayerMessage(final ByteBuffer buffer) {
        super(priority, messageType);
    
        this.stationId = buffer.getInt();
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        buffer.putInt(stationId);
    }
    /**
         02 00 18 52 36 2E 00 00 00 00 

     */
}
