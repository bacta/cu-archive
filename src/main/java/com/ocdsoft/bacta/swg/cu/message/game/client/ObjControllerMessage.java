package com.ocdsoft.bacta.swg.cu.message.game.client;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 struct __cppobj ObjControllerMessage : GameNetworkMessage
 {
     MessageQueue::Data *data;
     Archive::AutoVariable<unsigned long> flags;
     Archive::AutoVariable<long> message;
     Archive::AutoVariable<NetworkId> networkId;
     Archive::AutoVariable<float> value;
 }; 
 */
public class ObjControllerMessage extends GameNetworkMessage {

    private static final short priority = 0x5;
    private static final int messageType = SOECRC32.hashCode(ObjControllerMessage.class.getSimpleName());

    @Getter
    private final int flags;

    @Getter
    private final int message;

    @Getter
    private final long networkId;

    @Getter
    private final float value;
    
    @Getter
    private final ByteBuffer buffer;

    public ObjControllerMessage(final int flags, final int message, final long networkId, final float value) {
        super(priority, messageType);
     
        this.flags = flags;
        this.message = message;
        this.networkId = networkId;
        this.value = value;

        buffer = null;
    }

    public ObjControllerMessage(final ByteBuffer buffer) {
        super(priority, messageType);

        this.flags = buffer.getInt();
        this.message = buffer.getInt();
        this.networkId = buffer.getLong();
        this.value = buffer.getFloat();

        this.buffer = buffer;
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        buffer.putInt(flags);
        buffer.putInt(message);
        buffer.putLong(networkId);
        buffer.putFloat(value);
    }
    /**
         05 00 46 5E CE 80 21 00 00 00 71 00 00 00 1B 00
    00 00 01 00 00 00 93 0D 00 00 01 00 00 00 00 00
    00 00 00 00 00 00 00 00 00 00 00 00 80 3F 00 40
    A1 C4 FF 1E 6B 43 00 00 40 41 00 00 00 00 

     */
}
