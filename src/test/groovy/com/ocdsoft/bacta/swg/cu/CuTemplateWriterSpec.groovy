package com.ocdsoft.bacta.swg.cu

import com.ocdsoft.bacta.soe.ServerType
import com.ocdsoft.bacta.soe.util.SwgMessageTemplateWriter
import spock.lang.Specification

import java.nio.ByteBuffer

/**
 * Created by kburkhardt on 1/31/15.
 */
class CuTemplateWriterSpec extends Specification {

    def setupSpec() {
        System.setProperty("project.classpath", CuServer.class.getPackage().getName());
    }

    def "TemplateCreation"() {

        setup:
        SwgMessageTemplateWriter templateWriter = new SwgMessageTemplateWriter(ServerType.GAME)

        when:
        templateWriter.createFiles((int) 0xd5899226, (ByteBuffer) ByteBuffer.allocate(0));

        then:
        1 * templateWriter.writeController(_, _)
        1 * templateWriter.writeMessage(_, _)

        cleanup:
        templateWriter.deleteFiles((int) 0xd5899226)
    }
}
