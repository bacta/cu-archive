package com.ocdsoft.bacta.swg.cu.message.game.server.scene;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import com.ocdsoft.bacta.swg.cu.object.game.SceneObject;
import com.ocdsoft.bacta.swg.cu.object.game.Transform;

import java.nio.ByteBuffer;


/**
 struct __cppobj __declspec(align(8)) SceneCreateObjectByCrc : GameNetworkMessage
 {
     Archive::AutoVariable<NetworkId> m_networkId;
     Archive::AutoVariable<Transform> m_transform;
     Archive::AutoVariable<unsigned long> m_templateCrc;
     Archive::AutoVariable<bool> m_hyperspace;
 };
 */
 
public class SceneCreateObjectByCrc extends GameNetworkMessage {

    private static final short priority = 0x5;
    private static final int messageType = SOECRC32.hashCode(SceneCreateObjectByCrc.class.getSimpleName());
    
    private final long networkId;
    private final Transform transform;
    private final int templateCrc;
    private final boolean hyperspace;
    
    public SceneCreateObjectByCrc(SceneObject scno) {
        super(priority, messageType);

        this.networkId = scno.getNetworkId();
        this.transform = scno.getTransform();
        this.templateCrc = scno.getObjectTemplate().getCrcName();
        this.hyperspace = scno.isHyperspace();
    }

    public SceneCreateObjectByCrc(ByteBuffer buffer) {
        super(priority, messageType);

        this.networkId = buffer.getLong();
        this.transform = new Transform(buffer);
        this.templateCrc = buffer.getInt();
        this.hyperspace = BufferUtil.getBoolean(buffer);
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        buffer.putLong(networkId);
        transform.writeToBuffer(buffer);
        buffer.putInt(templateCrc);
        BufferUtil.putBoolean(buffer, hyperspace);
    }
}
