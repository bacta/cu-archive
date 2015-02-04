package com.ocdsoft.bacta.swg.cu.message.game.client;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;

import java.nio.ByteBuffer;

public class ChatRequestRoomList extends GameNetworkMessage {

    private static final short priority = 0x1;
    private static final int messageType = SOECRC32.hashCode(ChatRequestRoomList.class.getSimpleName());


    public ChatRequestRoomList() {
        super(priority, messageType);
        
    }

    public ChatRequestRoomList(final ByteBuffer buffer) {
        super(priority, messageType);
    
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
    
    }
    /**
         01 00 FA 2C 3D 4C 

     */
}
