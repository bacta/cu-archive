
/**
 * Created by kburkhardt on 12/9/14.
 */
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender

scan("10 seconds")

appender("STDOUT", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%d{ISO8601} %logger{5} [%-5level] %msg%n"
    }
}

logger("org.reflections",  WARN)
logger("io.netty",  WARN)
logger("com.couchbase",  WARN)
logger("com.ocdsoft",  DEBUG)
logger("com.ocdsoft.bacta.soe.controller.MultiController", TRACE)

root(INFO, ["STDOUT"])
