package com.ocdsoft.bacta.swg.cu.message.game.server;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;

import java.nio.ByteBuffer;


/**
 struct __cppobj ClientPermissionsMessage : GameNetworkMessage
 {
     Archive::AutoVariable<bool> m_canLogin;
     Archive::AutoVariable<bool> m_canCreateRegularCharacter;
     Archive::AutoVariable<bool> m_canCreateJediCharacter;
     Archive::AutoVariable<bool> m_canSkipTutorial;
 }; 
 */
public class ClientPermissionsMessage extends GameNetworkMessage {

    private static final short priority = 0x4;
    private static final int messageType = SOECRC32.hashCode(ClientPermissionsMessage.class.getSimpleName());


    private final boolean canLogin;
    private final boolean canCreateRegularCharacter;
    private final boolean canCreateJediCharacter;
    private final boolean canSkipTutorial;

    public ClientPermissionsMessage(boolean canLogin,
                                    boolean canCreateRegularCharacter,
                                    boolean canCreateJediCharacter,
                                    boolean canSkipTutorial) {
        super(priority, messageType);
        
        this.canLogin = canLogin;
        this.canCreateRegularCharacter = canCreateRegularCharacter;
        this.canCreateJediCharacter = canCreateJediCharacter;
        this.canSkipTutorial = canSkipTutorial;
    }

    public ClientPermissionsMessage(ByteBuffer buffer) {
        super(priority, messageType);

        this.canLogin = BufferUtil.getBoolean(buffer);
        this.canCreateRegularCharacter = BufferUtil.getBoolean(buffer);
        this.canCreateJediCharacter = BufferUtil.getBoolean(buffer);
        this.canSkipTutorial = BufferUtil.getBoolean(buffer);
    }
    
    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        BufferUtil.putBoolean(buffer, canLogin);
        BufferUtil.putBoolean(buffer, canCreateRegularCharacter);
        BufferUtil.putBoolean(buffer, canCreateJediCharacter);
        BufferUtil.putBoolean(buffer, canSkipTutorial);
    }
}
