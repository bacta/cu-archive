package com.ocdsoft.bacta.swg.cu.message.game.server.scene;

import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import com.ocdsoft.bacta.swg.cu.object.game.SceneObject;

import java.nio.ByteBuffer;

/**
 struct __cppobj UpdateContainmentMessage : GameNetworkMessage
 {
     Archive::AutoVariable<NetworkId> m_networkId;
     Archive::AutoVariable<NetworkId> m_containerId;
     Archive::AutoVariable<int> m_slotArrangement;
 }; 
 */
public class UpdateContainmentMessage extends GameNetworkMessage {

    private static final short priority = 0x4;
    private static final int messageType = SOECRC32.hashCode(UpdateContainmentMessage.class.getSimpleName());

    private final long networkId;
    private final long containerId;
    private final int slotArrangement;
    
    public UpdateContainmentMessage(SceneObject object) {
        super(priority, messageType);

        this.networkId = object.getNetworkId();
        this.containerId = object.getContainedBy();
        this.slotArrangement = object.getCurrentArrangement();
    }

    public UpdateContainmentMessage(SceneObject object, long containerId, int slotArrangement) {
        super(priority, messageType);

        this.networkId = object.getNetworkId();
        this.containerId = containerId;
        this.slotArrangement = slotArrangement;
    }

    public UpdateContainmentMessage(SceneObject object, SceneObject container, int slotArrangement) {
        super(priority, messageType);

        this.networkId = object.getNetworkId();
        this.containerId = container != null ? container.getNetworkId() : 0;
        this.slotArrangement = slotArrangement;
    }

    public UpdateContainmentMessage(ByteBuffer buffer) {
        super(priority, messageType);

        this.networkId = buffer.getLong();
        this.containerId = buffer.getLong();
        this.slotArrangement = buffer.getInt();
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        buffer.putLong(networkId);
        buffer.putLong(containerId);
        buffer.putInt(slotArrangement);
    }
}
