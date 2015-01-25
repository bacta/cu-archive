
/**
 * Created by kburkhardt on 12/9/14.
 */
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender

appender("STDOUT", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%d{ISO8601} %logger{5} [%-5level] %msg%n"
    }
}

logger("org.reflections",  WARN)
logger("io.netty",  WARN)

root(TRACE, ["STDOUT"])
