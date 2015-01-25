package com.ocdsoft.bacta.swg.cu.message.login.client;

import com.ocdsoft.bacta.engine.utils.BufferUtil;
import com.ocdsoft.bacta.soe.message.GameNetworkMessage;
import com.ocdsoft.bacta.soe.util.SOECRC32;
import lombok.Getter;

import java.nio.ByteBuffer;

public class LoginClientId extends GameNetworkMessage {

    private static final short priority = 0x4;
    private static final int messageType = SOECRC32.hashCode(LoginClientId.class.getSimpleName());


    @Getter
    private final String username;

    @Getter
    private final String password;

    @Getter
    private final String clientVersion;

    public LoginClientId(final String username, final String password, final String clientVersion) {
        super(priority, messageType);

        this.username = username;
        this.password = password;
        this.clientVersion = clientVersion;
    }

    public LoginClientId(ByteBuffer buffer) {
        super(priority, messageType);

        username = BufferUtil.getAscii(buffer);
        password = BufferUtil.getAscii(buffer);
        clientVersion = BufferUtil.getAscii(buffer);
    }

    @Override
    public void writeToBuffer(ByteBuffer buffer) {
        BufferUtil.putAscii(buffer, username);
        BufferUtil.putAscii(buffer, password);
        BufferUtil.putAscii(buffer, clientVersion);
    }

    /**
     00 09 00 00 04 00 96 1F 13 41 04 00 6B 79 6C 65
     04 00 6B 79 6C 65 0E 00 32 30 30 35 30 34 30 38
     2D 31 38 3A 30 30 00 10 D3

     */
}
