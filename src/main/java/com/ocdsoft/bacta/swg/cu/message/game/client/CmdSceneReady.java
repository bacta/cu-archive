package com.ocdsoft.bacta.swg.cu.message.game.client;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;

import java.nio.ByteBuffer;

/**
 struct __cppobj CmdSceneReady : GameNetworkMessage
 {
 };
 */
public class CmdSceneReady extends GameNetworkMessage {

    private static final short priority = 0x1;
    private static final int messageType = SOECRC32.hashCode(CmdSceneReady.class.getSimpleName());


    public CmdSceneReady() {
        super(priority, messageType);
        
    }

    public CmdSceneReady(final ByteBuffer buffer) {
        super(priority, messageType);
    
    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
    
    }
    /**
         01 00 22 1C FD 43 

     */
}
