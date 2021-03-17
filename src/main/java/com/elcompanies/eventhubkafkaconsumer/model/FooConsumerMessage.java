package com.elcompanies.eventhubkafkaconsumer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.Acknowledgment;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Slf4j
public class FooConsumerMessage extends AbstractConsumerMessage {
    private Integer processAtMinutes;
    private String message;

    @Override
    public void processMessage(Acknowledgment ack) {
        log.info("Received record {}", this);
        log.info("Message {}", message);
        log.info("Should be processed at {}", processAtMinutes);
        log.info("Commit offset for message {}", message);
        ack.acknowledge();
    }
}
