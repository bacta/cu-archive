package com.ocdsoft.bacta.swg.cu.message.game.server;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import com.ocdsoft.bacta.swg.shared.localization.StringId;

import java.nio.ByteBuffer;

/**
 * Created by Kyle on 3/15/14.
 */

/**
 struct __cppobj ClientRandomNameResponse : GameNetworkMessage
 {
     Archive::AutoVariable<std::string > m_creatureTemplate;
     Archive::AutoVariable<UnicodeString > m_name;
     Archive::AutoVariable<StringId> m_errorMessage;
 }; 
 */
public class ClientRandomNameResponse extends GameNetworkMessage {

    private static final short priority = 0x9;
    private static final int messageType = SOECRC32.hashCode(ClientRandomNameResponse.class.getSimpleName());

    private final String creatureTemplate;
    private final String name;
    private final StringId errorMessage;
    
    public ClientRandomNameResponse(String creatureTemplate, String name, StringId errorMessage) {
        super(priority, messageType);

        this.creatureTemplate = creatureTemplate;
        this.name = name;
        this.errorMessage = errorMessage;
    }

    public ClientRandomNameResponse(ByteBuffer buffer) {
        super(priority, messageType);

        this.creatureTemplate = BufferUtil.getAscii(buffer);
        this.name = BufferUtil.getUnicode(buffer);
        this.errorMessage = new StringId(buffer);
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        BufferUtil.putAscii(buffer, creatureTemplate);
        BufferUtil.putUnicode(buffer, name);
        errorMessage.writeToBuffer(buffer);
    }
}
