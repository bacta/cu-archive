package com.ocdsoft.bacta.swg.cu.message.game.server;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import com.ocdsoft.bacta.swg.shared.localization.StringId;

import java.nio.ByteBuffer;

/**
 * Created by kburkhardt on 2/2/15.
 * 
 struct __cppobj ClientCreateCharacterFailed : GameNetworkMessage
 {
 Archive::AutoVariable<UnicodeString > m_name;
 Archive::AutoVariable<StringId> m_errorMessage;
 };
 */
public class ClientCreateCharacterFailed extends GameNetworkMessage {

    private static final short priority = 0x3;
    private static final int messageType = SOECRC32.hashCode(ClientCreateCharacterFailed.class.getSimpleName());
    
    private final String name;
    private final StringId errorMessage;
    
    /**
     * Responses are found in the ui package starting with "name_declined"
     */
    public ClientCreateCharacterFailed(String name, String errorKey) {
        super(priority, messageType);

        this.name = name;
        this.errorMessage = new StringId("ui", errorKey);
    }

    public ClientCreateCharacterFailed(ByteBuffer buffer) {
        super(priority, messageType);

        this.name = BufferUtil.getUnicode(buffer);
        this.errorMessage = new StringId(buffer);
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        BufferUtil.putUnicode(buffer, name);
        errorMessage.writeToBuffer(buffer);
    }


}
