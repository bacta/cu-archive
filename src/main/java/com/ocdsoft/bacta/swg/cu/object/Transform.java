package com.ocdsoft.bacta.swg.cu.object;

import com.ocdsoft.bacta.engine.buffer.ByteBufferWritable;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import lombok.Getter;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;
import java.nio.ByteBuffer;

/**
 * Created by kburkhardt on 2/1/15.

const struct Transform
{
    float matrix[3][4];
};
 */

public class Transform implements ByteBufferWritable {

    @Getter
    private Vector3f position;
    
    @Getter
    private Quat4f rotation;

    public Transform() {
        this.position = new Vector3f();
        this.rotation = new Quat4f();
    }
    
    public Transform(Vector3f position, Quat4f rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public Transform(ByteBuffer buffer) {
        this.rotation = BufferUtil.getQuat4f(buffer);
        this.position = BufferUtil.getVector3f(buffer);
    }
    
    public void setPosition(Vector3f position) {
        this.position = position;
    }
    
    public void setRotation(Quat4f rotation) {
        this.rotation = rotation;
    }
    
    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        BufferUtil.putQuat4f(buffer, rotation);
        BufferUtil.putVector3f(buffer, position);
    }
}
