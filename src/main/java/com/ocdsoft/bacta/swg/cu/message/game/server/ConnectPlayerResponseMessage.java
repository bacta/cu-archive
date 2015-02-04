package com.ocdsoft.bacta.swg.cu.message.game.server;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * Created by kburkhardt on 2/3/15.
 struct __cppobj ConnectPlayerResponseMessage : GameNetworkMessage
 {
    Archive::AutoVariable<long> m_result;
 };
 */
public class ConnectPlayerResponseMessage extends GameNetworkMessage {

    private static final short priority = 0x3;
    private static final int messageType = SOECRC32.hashCode(ConnectPlayerResponseMessage.class.getSimpleName());

    @Getter
    private final int result;
    
    public ConnectPlayerResponseMessage(final int result) {
        super(priority, messageType); //ConnectPlayerResponseMessage

        this.result = result;
    }

    public ConnectPlayerResponseMessage(final ByteBuffer buffer) {
        super(priority, messageType); //ConnectPlayerResponseMessage

        this.result = buffer.getInt();
    }
    
    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        buffer.putInt(result);
    }
}
