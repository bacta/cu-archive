package com.ocdsoft.bacta.swg.cu.data.command;

import com.ocdsoft.bacta.engine.buffer.ByteBufferWritable;

import java.nio.ByteBuffer;

/**
 * Created by kburkhardt on 2/7/15.
 enum Command::Priority
 {
 CP_Immediate = 0x0,
 CP_Front = 0x1,
 CP_Normal = 0x2,
 CP_Default = 0x3,
 CP_NumberOfPriorities = 0x4,
 };
 */
public enum CommandPriority implements ByteBufferWritable {
    CP_Immediate(0x0),
    CP_Front(0x1),
    CP_Normal(0x2),
    CP_Default(0x3),
    CP_NumberOfPriorities(0x4);

    private final int value;
    
    CommandPriority(int value) {
        this.value = value;
    }
    
    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
        buffer.putInt(value);
    }
    
}
