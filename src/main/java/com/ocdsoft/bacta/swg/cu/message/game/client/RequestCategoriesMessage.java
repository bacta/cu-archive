package com.ocdsoft.bacta.swg.cu.message.game.client;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;

import java.nio.ByteBuffer;

public class RequestCategoriesMessage extends GameNetworkMessage {

    private static final short priority = 0x2;
    private static final int messageType = SOECRC32.hashCode(RequestCategoriesMessage.class.getSimpleName());


    public RequestCategoriesMessage() {
        super(priority, messageType);
        
    }

    public RequestCategoriesMessage(final ByteBuffer buffer) {
        super(priority, messageType);
    
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
    
    }
    /**
         02 00 5F E2 98 F8 02 00 65 6E 

     */
}
