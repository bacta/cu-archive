package com.ocdsoft.bacta.swg.cu.message.game.client;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import lombok.Getter;

import java.nio.ByteBuffer;


/**
 struct __cppobj ClientIdMsg : GameNetworkMessage
 {
     Archive::AutoVariable<unsigned long> m_gameBitsToClear;
     Archive::AutoArray<unsigned char> token;
     Archive::AutoVariable<std::string > version;
     char *tokenData;
 }; 
 */
public class ClientIdMsg extends GameNetworkMessage {

    private static final short priority = 0x4;
    private static final int messageType = SOECRC32.hashCode(ClientIdMsg.class.getSimpleName());

    @Getter
    private final int gameBitsToClear;

    @Getter
    private final String token;

    @Getter
    private final String clientVersion;
    
    public ClientIdMsg(final int gameBitsToClear,
                       final String token,
                       final String clientVersion) {
        super(priority, messageType);
        
        this.gameBitsToClear = gameBitsToClear;
        this.token = token;
        this.clientVersion = clientVersion;
    }

    public ClientIdMsg(ByteBuffer buffer) {
        super(priority, messageType);
    
        gameBitsToClear = buffer.getInt();
        token = BufferUtil.getBinaryString(buffer);
        clientVersion = BufferUtil.getAscii(buffer);
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        buffer.putInt(gameBitsToClear);
        BufferUtil.putBinaryString(buffer, token);
        BufferUtil.putAscii(buffer, clientVersion);
    }
    /**
         04 00 26 92 89 D5 00 00 00 00 25 00 00 00 31 35
    32 34 31 32 37 38 37 36 37 37 34 30 36 39 35 33
    32 32 36 38 33 35 34 36 32 31 39 36 35 30 36 37
    35 34 33 0E 00 32 30 30 35 31 30 31 30 2D 31 37
    3A 30 30 

     */
}
