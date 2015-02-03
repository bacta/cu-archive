package com.ocdsoft.bacta.swg.cu.message.game.client;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;

import java.nio.ByteBuffer;

public class LagRequest extends GameNetworkMessage {

    private static final short priority = 0x1;
    private static final int messageType = SOECRC32.hashCode(LagRequest.class.getSimpleName());


    public LagRequest() {
        super(priority, messageType);
        
    }

    public LagRequest(ByteBuffer buffer) {
        super(priority, messageType);
    
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
    
    }
    /**
         01 00 E0 5E 80 31 

     */
}
