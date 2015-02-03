package com.ocdsoft.bacta.swg.cu.object.game;

import com.ocdsoft.bacta.engine.buffer.ByteBufferWritable;

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

    private Vector3f position;
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
        float rx = buffer.getFloat();
        float ry = buffer.getFloat();
        float rz = buffer.getFloat();
        float rw = buffer.getFloat();

        this.rotation = new Quat4f(rx, ry, rz, rw);
        
        float px = buffer.getFloat();
        float pz = buffer.getFloat();
        float py = buffer.getFloat();
        this.position = new Vector3f(px, pz, py);
        
    }
    
    public void setPosition(Vector3f position) {
        this.position = position;
    }
    
    public void setRotation(Quat4f rotation) {
        this.rotation = rotation;
    }
    
    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        buffer.putFloat(rotation.x); // X Direction
        buffer.putFloat(rotation.y); // Y Direction
        buffer.putFloat(rotation.z); // Z Direction
        buffer.putFloat(rotation.w); // W Direction
        buffer.putFloat(position.x); // X Position
        buffer.putFloat(position.z); // Z Position
        buffer.putFloat(position.y); // Y Position
    }
}
