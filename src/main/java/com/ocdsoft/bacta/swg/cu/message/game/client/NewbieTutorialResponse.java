package com.ocdsoft.bacta.swg.cu.message.game.client;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;

import java.nio.ByteBuffer;

/**
 struct __cppobj NewbieTutorialResponse : GameNetworkMessage
 {
    Archive::AutoVariable<std::string > m_response;
 };
 */
public class NewbieTutorialResponse extends GameNetworkMessage {

    private static final short priority = 0x2;
    private static final int messageType = SOECRC32.hashCode(NewbieTutorialResponse.class.getSimpleName());

    private final String response;

    public NewbieTutorialResponse(final String response) {
        super(priority, messageType);
        
        this.response = response;
    }

    public NewbieTutorialResponse(final ByteBuffer buffer) {
        super(priority, messageType);
    
        this.response = BufferUtil.getAscii(buffer);
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        BufferUtil.putAscii(buffer, response);
    }
    /**
         02 00 AD FB 88 CA 0B 00 63 6C 69 65 6E 74 52 65
    61 64 79 // clientReady

     */
}
