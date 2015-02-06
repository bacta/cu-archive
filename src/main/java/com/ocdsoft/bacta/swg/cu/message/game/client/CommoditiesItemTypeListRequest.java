package com.ocdsoft.bacta.swg.cu.message.game.client;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;

import java.nio.ByteBuffer;

public class CommoditiesItemTypeListRequest extends GameNetworkMessage {

    private static final short priority = 0x2;
    private static final int messageType = 0x48f493c5;//SOECRC32.hashCode(CommoditiesItemTypeListRequest.class.getSimpleName());

    private final String typeString;
    
    public CommoditiesItemTypeListRequest(final String typeString) {
        super(priority, messageType);
        
        this.typeString = typeString;
    }

    public CommoditiesItemTypeListRequest(final ByteBuffer buffer) {
        super(priority, messageType);

        this.typeString = BufferUtil.getAscii(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.putAscii(buffer, typeString);
    }
    /**
         02 00 C5 93 F4 48 00 00 

     */
}
