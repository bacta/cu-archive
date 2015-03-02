package com.ocdsoft.bacta.swg.cu.message;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;

import java.nio.ByteBuffer;

/**
struct __cppobj ErrorMessage : GameNetworkMessage
{
    Archive::AutoVariable<std::string > errorName;
    Archive::AutoVariable<std::string > description;
    Archive::AutoVariable<bool> fatal;
};
 */
public final class ErrorMessage extends GameNetworkMessage {

    private static final short priority = 0x2;
    private static final int messageType = SOECRC32.hashCode(ErrorMessage.class.getSimpleName());

    private final String errorName;
    private final String description;
    private final boolean fatal;

    public ErrorMessage(final String errorName, final String description, boolean fatal) {
        super(priority, messageType);

        this.errorName = errorName;
        this.description = description;
        this.fatal = fatal;
    }

    public ErrorMessage(ByteBuffer buffer) {
        super(priority, messageType);

        this.errorName = BufferUtil.getAscii(buffer);
        this.description = BufferUtil.getAscii(buffer);
        this.fatal = BufferUtil.getBoolean(buffer);
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        BufferUtil.putAscii(buffer, errorName);
        BufferUtil.putAscii(buffer, description);
        BufferUtil.putBoolean(buffer, fatal);
    }
}
