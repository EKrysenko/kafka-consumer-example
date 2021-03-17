package com.elcompanies.eventhubkafkaconsumer.consumer;

import com.elcompanies.eventhubkafkaconsumer.model.ConsumerMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class KafkaMessageConsumer {

    @KafkaListener(
            topics = "${topics.subscriber}",
            groupId = "${spring.kafka.consumer.group-id}"
//            For implicit partition
//            topicPartitions = {@TopicPartition(topic = "${topics.subscriber}", partitions = {"0"})}
    )
    // To use auto-commit remove ContainerProperties.AckMode.MANUAL_IMMEDIATE from KafkaConsumerConfig
//    public void receive(String message) throws InterruptedException {
//        log.info("Received message from topic: {}", message);
//        Thread.sleep(60000);
//    }
    public void onMessage(ConsumerMessage record, Acknowledgment ack) {
        log.info("Received record {}", record);
        final String message = record.getMessage();
        final int processAtMinutes = record.getProcessAtMinutes();
        final int minutesNow = LocalDateTime.now().getMinute();
        if (minutesNow < processAtMinutes) {
            log.error("Сообщение с кодом {} пришло слишком рано, будет обработано через {} минут",
                    processAtMinutes, processAtMinutes - minutesNow);
            ack.nack(10000);
        } else {
            log.info("Commit offset for message {}", message);
            ack.acknowledge();
        }
    }
}
