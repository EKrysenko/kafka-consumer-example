package com.elcompanies.eventhubkafkaconsumer.consumer;

import com.elcompanies.eventhubkafkaconsumer.model.AbstractConsumerMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaMessageConsumer {

    @KafkaListener(
            topics = "${topics.subscriber}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void onMessage(AbstractConsumerMessage record, Acknowledgment ack) {
        record.processMessage(ack);
    }
}
