package com.ocdsoft.bacta.swg.cu.message.game.object;

import com.ocdsoft.bacta.swg.object.ObjControllerId;
import com.ocdsoft.bacta.swg.object.ObjectController;

import java.nio.ByteBuffer;

/**
 struct __cppobj __declspec(align(8)) MessageQueueCommandQueueEnqueue : MessageQueue::Data
 {
     unsigned int m_sequenceId;
     unsigned int m_commandHash;
     NetworkId m_targetId;
     UnicodeString m_params;
 };
 */
@ObjControllerId(0x116)
public class CommandQueueEnqueue implements ObjectController {

    public CommandQueueEnqueue() {

    }

    public CommandQueueEnqueue(final ByteBuffer buffer) {

    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
    
    }
    /**
         05 00 46 5E CE 80 23 00 00 00 16 01 00 00 2B 00
    00 00 01 00 00 00 00 00 00 00 00 00 00 00 45 B1
    2A 39 00 00 00 00 00 00 00 00 00 00 00 00 

     */
}
