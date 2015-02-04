package com.ocdsoft.bacta.swg.cu.message.game.server;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;

import javax.vecmath.Vector3f;
import java.nio.ByteBuffer;

/**
 * Created by kburkhardt on 2/2/15.
 * 
 struct __cppobj CmdStartScene : GameNetworkMessage
 {
     Archive::AutoVariable<bool> disableWorldSnapshot;
     Archive::AutoVariable<NetworkId> objectId;
     Archive::AutoVariable<std::string > sceneName;
     Archive::AutoVariable<Vector> startPosition;
     Archive::AutoVariable<float> startYaw;
     Archive::AutoVariable<std::string > templateName;
     Archive::AutoVariable<__int64> timeSeconds;
     Archive::AutoVariable<long> serverEpoch;
 };
 */

public class CmdStartScene extends GameNetworkMessage {

    private static final short priority = 0x8;
    private static final int messageType = SOECRC32.hashCode(CmdStartScene.class.getSimpleName());

    private final boolean disableWorldSnapshot;
    private final long networkId;
    private final String sceneName;
    private final Vector3f startPosition;
    private final float startYaw;
    private final String templateName;
    private final long timeSeconds;
    private final int serverEpoch;

    public CmdStartScene(final boolean disableWorldSnapshot,
                         final long networkId,
                         final String sceneName,
                         final Vector3f startPosition,
                         final float startYaw,
                         final String templateName,
                         final long timeSeconds,
                         final int serverEpoch) {
        super(priority, messageType);

        this.disableWorldSnapshot = disableWorldSnapshot;
        this.networkId = networkId;
        this.sceneName = sceneName;
        this.startPosition = startPosition;
        this.startYaw = startYaw;
        this.templateName = templateName;
        this.timeSeconds = timeSeconds;
        this.serverEpoch = serverEpoch;
    }

    public CmdStartScene(final ByteBuffer buffer) {
        super(priority, messageType);

        this.disableWorldSnapshot = BufferUtil.getBoolean(buffer);
        this.networkId = buffer.getLong();
        this.sceneName = BufferUtil.getAscii(buffer);
        this.startPosition = new Vector3f(buffer.getFloat(), buffer.getFloat(), buffer.getFloat());
        this.startYaw = buffer.getFloat();
        this.templateName = BufferUtil.getAscii(buffer);
        this.timeSeconds = buffer.getLong();
        this.serverEpoch = buffer.getInt();
    }
    
    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        BufferUtil.putBoolean(buffer, disableWorldSnapshot);
        buffer.putLong(networkId);
        BufferUtil.putAscii(buffer, sceneName);
        BufferUtil.putVector3f(buffer, startPosition);
        buffer.putFloat(startYaw);
        BufferUtil.putAscii(buffer, templateName);
        buffer.putLong(timeSeconds);
        buffer.putInt(serverEpoch);
    }
}
