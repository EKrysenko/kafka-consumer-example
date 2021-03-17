package com.elcompanies.eventhubkafkaconsumer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.kafka.support.Acknowledgment;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "messageType", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = FooConsumerMessage.class, name = "FOO"),
        @JsonSubTypes.Type(value = BarConsumerMessage.class, name = "BAR")
})
public abstract class AbstractConsumerMessage {
    @JsonProperty("messageType")
    private MessageType messageType;

    abstract public void processMessage(Acknowledgment acknowledgment);

    public enum MessageType {
        FOO, BAR;
    }
}
