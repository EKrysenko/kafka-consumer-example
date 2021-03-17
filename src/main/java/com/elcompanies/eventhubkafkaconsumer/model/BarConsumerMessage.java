package com.elcompanies.eventhubkafkaconsumer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.Acknowledgment;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Slf4j
public class BarConsumerMessage extends AbstractConsumerMessage {
    private List<String> barNames;

    @Override
    public void processMessage(Acknowledgment ack) {
        log.info("Received record {}", this);
        log.info("Bars list {}", barNames);
        log.info("Commit offset for message {}", this);
        ack.acknowledge();
    }
}
