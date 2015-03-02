package com.ocdsoft.bacta.swg.cu.message.game.object;

import com.ocdsoft.bacta.swg.object.ObjControllerId;
import com.ocdsoft.bacta.swg.object.ObjectController;

import java.nio.ByteBuffer;

@ObjControllerId(0x126)
public class TargetUpdate implements ObjectController {

    public TargetUpdate() {

    }

    public TargetUpdate(final ByteBuffer buffer) {

    }

    @Override
    public void writeToBuffer(final ByteBuffer buffer) {
    
    }
    /**
         00 09 00 0A 05 00 46 5E CE 80 83 00 00 00 26 01
    00 00 30 00 00 00 01 00 00 00 00 00 00 00 00 00
    00 00 00 00 00 00 

     */
}
