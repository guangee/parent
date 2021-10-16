import ch.qos.logback.classic.PatternLayout
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.classic.filter.LevelFilter
import com.coding.common.AmqpLogbackAppender
import com.coding.utils.JsonUtil
import com.coding.utils.StringUtil
import com.google.common.collect.Maps
import net.logstash.logback.appender.LogstashSocketAppender
import net.logstash.logback.encoder.LogstashEncoder
import org.apache.commons.lang.math.NumberUtils
import org.apache.commons.lang3.StringUtils
import org.springframework.boot.logging.logback.ColorConverter
import org.springframework.boot.logging.logback.ExtendedWhitespaceThrowableProxyConverter
import org.springframework.boot.logging.logback.WhitespaceThrowableProxyConverter

import java.nio.charset.Charset

import static ch.qos.logback.core.spi.FilterReply.ACCEPT
import static ch.qos.logback.core.spi.FilterReply.NEUTRAL

scan("10 seconds")
conversionRule("clr", ColorConverter)
conversionRule("wex", WhitespaceThrowableProxyConverter)
conversionRule("wEx", ExtendedWhitespaceThrowableProxyConverter)

/*变量定义*/
def udp_host = getProperty("LOG_UDP_HOST", "8.142.1.157")
def udp_port = getPropertyInt("LOG_UDP_PORT", 5000)
def project = getProperty("PROJECT_NAME", "default")

def amqp_host = getProperty("AMQP_HOST", "redis.coding-space.cn")
def amqp_port = getPropertyInt("AMQP_PORT", 5672)
def amqp_username = getProperty("AMQP_USERNAME", "admin")
def amqp_password = getProperty("AMQP_PASSWORD", "19941108Aa")
def amqp_exchangeName = getProperty("AMQP_EXCHANGE_NAME", "elk_exchange")
def amqp_routingKeyPattern = getProperty("AMQP_ROUTING_KEY_PATTERN", "elk_key")
def amqp_applicationId = getProperty("AMQP_APPLICATION_ID", "spring-cloud-system")
def amqp_virtualHost = getProperty("AMQP_VIRTUALHOST", "/")
def amqp_exchangeType = getProperty("AMQP_EXCHANGE_TYPE", "direct")

def custom_fields = getCustomFields(project)

/*日志打印结构*/
appender("CONSOLE", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){red} %clr(%-5level) %clr(%-40.40logger{39}){cyan} %wEx %m%n"
        charset = Charset.forName("utf8")
    }
    filter(LevelFilter) {
        level = DEBUG
        onMatch = ACCEPT
        onMismatch = NEUTRAL
    }
}
appender("AMQP", AmqpLogbackAppender) {
    layout(PatternLayout) {
        pattern = "%level"
    }
    encoder(LogstashEncoder) {
        customFields = custom_fields
    }
    host = amqp_host
    port = amqp_port
    username = amqp_username
    password = amqp_password
    exchangeName = amqp_exchangeName
    routingKeyPattern = amqp_routingKeyPattern
    applicationId = amqp_applicationId
    virtualHost = amqp_virtualHost
    declareExchange = true
    exchangeType = amqp_exchangeType
    generateId = true
    charset = Charset.forName("UTF-8")
    durable = true
    deliveryMode = "PERSISTENT"
}

appender("UDP", LogstashSocketAppender) {
    customFields = custom_fields
    host = udp_host
    port = udp_port
}
root(INFO, ["CONSOLE", "AMQP"])


/*--工具类，读取环境变量的工具--*/

static def getCustomFields(project) {
    Map<String, String> map = Maps.newHashMapWithExpectedSize(1)
    map.put("project", project)
    return JsonUtil.toJson(map)
}

static def getPropertyInt(name, defaultValue) {
    return NumberUtils.toInt(String.valueOf(getProperty(name, defaultValue)))
}

static def getProperty(name, defaultValue) {
    if (StringUtils.isBlank(name)) {
        return defaultValue
    }
    def value = System.getenv(name)
    if (StringUtil.isBlank(value)) {
        return defaultValue
    }
    return value
}