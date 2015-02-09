package com.ocdsoft.bacta.swg.cu

import com.ocdsoft.bacta.soe.ServerType
import com.ocdsoft.bacta.soe.util.SwgMessageTemplateWriter
import spock.lang.Specification

import java.nio.ByteBuffer
import java.nio.ByteOrder

/**
 * Created by kburkhardt on 1/31/15.
 */
class CuTemplateWriterSpecDebug extends Specification {

    def setupSpec() {
        String baseFilePath = CuServer.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        File file = new File(baseFilePath);
        String tempDir = file.getParentFile().getParent();

        System.setProperty("base.classpath", CuServer.class.getPackage().getName());
        System.setProperty("template.filepath", tempDir);
    }

    def "TemplateCreation"() {

        setup:
        SwgMessageTemplateWriter templateWriter = new SwgMessageTemplateWriter(ServerType.GAME)
        ByteBuffer buffer = ByteBuffer.allocate(12).order(ByteOrder.LITTLE_ENDIAN);
        buffer.putShort(6, (short) 0x04);
        buffer.putInt(8, (int) 0xd5899226);

        when:
        templateWriter.createFiles((int) 0xd5899226, buffer);

        then:
        1 * templateWriter.writeController(_)
        1 * templateWriter.writeMessage(_)

        cleanup:
        templateWriter.deleteFiles((int) 0xd5899226)
    }
    
    
}
