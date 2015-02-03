package com.ocdsoft.bacta.swg.cu.message.game.client;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import lombok.Getter;

import java.nio.ByteBuffer;

public class LagReport extends GameNetworkMessage {

    private static final short priority = 0x2;
    private static final int messageType = SOECRC32.hashCode(LagReport.class.getSimpleName());

    @Getter
    private final int value1;
    
    @Getter
    private final int value2;

    public LagReport(final int value1, final int value2) {
        super(priority, messageType);

        this.value1 = value1;
        this.value2 = value2;
    }

    public LagReport(ByteBuffer buffer) {
        super(priority, messageType);

        this.value1 = buffer.getInt();
        this.value2 = buffer.getInt();
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        buffer.putInt(value1);
        buffer.putInt(value2);
    }
    /**
         02 00 85 2F ED C5 00 03 00 00 00 00 00 00 

     */
}
