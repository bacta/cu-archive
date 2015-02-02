package com.ocdsoft.bacta.swg.cu.message.game.client;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 struct __cppobj ClientRandomNameRequest : GameNetworkMessage
 {
    Archive::AutoVariable<std::string > m_creatureTemplate;
 };
 */
public class ClientRandomNameRequest extends GameNetworkMessage {

    private static final short priority = 0x9;
    private static final int messageType = SOECRC32.hashCode(ClientRandomNameRequest.class.getSimpleName());

    @Getter
    private final String creatureTemplate;

    public ClientRandomNameRequest(String creatureTemplate) {
        super(priority, messageType);
        
        this.creatureTemplate = creatureTemplate;
    }

    public ClientRandomNameRequest(ByteBuffer buffer) {
        super(priority, messageType);

        creatureTemplate = BufferUtil.getAscii(buffer);
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        BufferUtil.putAscii(buffer, creatureTemplate);
    }
    /**
         00 09 00 02 02 00 D1 B6 D1 D6 25 00 6F 62 6A 65
    63 74 2F 63 72 65 61 74 75 72 65 2F 70 6C 61 79
    65 72 2F 68 75 6D 61 6E 5F 6D 61 6C 65 2E 69 66
    66 00 2A F9 

     */
}
