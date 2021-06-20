package com.coding.common;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.encoder.Encoder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.logback.AmqpAppender;

/**
 * @author 关卫明
 */
public class AmqpLogbackAppender extends AmqpAppender {
    @Getter
    @Setter
    private Encoder<ILoggingEvent> encoder;

    /**
     * We remove the default message layout and replace with the JSON {@link Encoder}
     */
    @Override
    public Message postProcessMessageBeforeSend(Message message, Event event) {
        return new Message(this.encoder.encode(event.getEvent()), message.getMessageProperties());
    }

    @Override
    public void start() {
        super.start();
        encoder.setContext(getContext());

        if (!encoder.isStarted()) {
            encoder.start();
        }

    }

    @Override
    public void stop() {
        super.stop();
        encoder.stop();
    }
}
