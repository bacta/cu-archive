package com.ocdsoft.bacta.swg.cu.message.game.client;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * No provided struct
 */
public class ClientInactivityMessage extends GameNetworkMessage {

    private static final short priority = 0x2;
    private static final int messageType = SOECRC32.hashCode(ClientInactivityMessage.class.getSimpleName());

    @Getter
    private final boolean inactive;

    public ClientInactivityMessage(final boolean inactive) {
        super(priority, messageType);
        
        this.inactive = inactive;
    }

    public ClientInactivityMessage(final ByteBuffer buffer) {
        super(priority, messageType);
    
        this.inactive = BufferUtil.getBoolean(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.putBoolean(buffer, inactive);
    }
    /**
         02 00 25 53 5D 0F 00 

     */
}
