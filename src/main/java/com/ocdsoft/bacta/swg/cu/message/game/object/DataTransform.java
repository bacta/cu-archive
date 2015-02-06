package com.ocdsoft.bacta.swg.cu.message.game.object;

import com.ocdsoft.bacta.swg.object.ObjControllerId;
import com.ocdsoft.bacta.swg.object.ObjectController;

import java.nio.ByteBuffer;

@ObjControllerId(0x71)
public class DataTransform implements ObjectController {

    public DataTransform() {

    }

    public DataTransform(final ByteBuffer buffer) {

    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
    
    }
    /**
         05 00 46 5E CE 80 21 00 00 00 71 00 00 00 2B 00
    00 00 01 00 00 00 90 22 00 00 01 00 00 00 00 00
    00 00 00 00 00 00 00 00 00 00 00 00 80 3F 00 40
    A1 C4 FF 1E 6B 43 00 00 40 41 00 00 00 00 

     */
}
