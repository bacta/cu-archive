package com.ocdsoft.bacta.swg.cu.message.game.server;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;

import java.nio.ByteBuffer;

/**
 * Created by kburkhardt on 2/2/15.
 */
public class UnkByteFlag extends GameNetworkMessage {

    private static final short priority = 0x2;
    private static final int messageType = 0x7102B15F;//SOECRC32.hashCode(UnkByteFlag.class.getSimpleName());

    private final byte unk;
    
    public UnkByteFlag(final byte unk) {
        super(priority, messageType);

        this.unk = unk;
    }

    public UnkByteFlag(final ByteBuffer buffer) {
        super(priority, messageType);

        this.unk = buffer.get();
    }
    
    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        buffer.put(unk);
    }
}
