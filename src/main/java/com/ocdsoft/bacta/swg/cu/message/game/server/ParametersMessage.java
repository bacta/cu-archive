package com.ocdsoft.bacta.swg.cu.message.game.server;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;

import java.nio.ByteBuffer;

/**
 * Created by kburkhardt on 2/3/15.
 *
 struct __cppobj ParametersMessage : GameNetworkMessage
 {
    Archive::AutoVariable<int> m_weatherUpdateInterval;
 }; 
 */
public class ParametersMessage extends GameNetworkMessage {

    private static final short priority = 0x2;
    private static final int messageType = SOECRC32.hashCode(ParametersMessage.class.getSimpleName());

    private final int weatherUpdateInterval;
    
    public ParametersMessage(int weatherUpdateInterval) {
        super(priority, messageType);
        
        this.weatherUpdateInterval = weatherUpdateInterval;
    }

    public ParametersMessage(final ByteBuffer buffer) {
        super(priority, messageType);

        this.weatherUpdateInterval = buffer.getInt();
    }
    
    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        buffer.putInt(weatherUpdateInterval);
    }
}
