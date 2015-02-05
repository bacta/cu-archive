package com.ocdsoft.bacta.swg.cu.message.game.client;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;

import java.nio.ByteBuffer;

public class NewTicketActivityMessage extends GameNetworkMessage {

    private static final short priority = 0x2;
    private static final int messageType = SOECRC32.hashCode(NewTicketActivityMessage.class.getSimpleName());


    public NewTicketActivityMessage() {
        super(priority, messageType);
        
    }

    public NewTicketActivityMessage(final ByteBuffer buffer) {
        super(priority, messageType);
    
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
    
    }
    /**
         02 00 78 4E 4F 27 00 00 00 00 

     */
}
