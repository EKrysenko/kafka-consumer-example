package com.elcompanies.eventhubkafkaconsumer.consumer;

import com.elcompanies.eventhubkafkaconsumer.model.ConsumerMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
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
    public void onMessage(ConsumerRecord<String, ConsumerMessage> record, Acknowledgment ack) {
        log.info("Received record from topic: {}, partition: {}, offset: {}, message: {}",
                record.topic(),  record.partition(), record.offset(), record.value());
        final String message = record.value().getMessage();
        final int processAtMinutes = record.value().getProcessAtMinutes();
        final int minutesNow = LocalDateTime.now().getMinute();
//        if (minutesNow < processAtMinutes) {
//            log.error("Сообщение с кодом {} пришло слишком рано, будет обработано через {} минут",
//                    processAtMinutes, processAtMinutes - minutesNow);
//            ack.nack(20);
//        } else {
//            log.info("Commit offset for message {}", message);
//            ack.acknowledge();
//        }
//        log.error("Вижу сообщение {}, но не коммичу оффсет {}", message, record.offset());
        log.error("Вижу сообщение {}, коммичу оффсет {}", message, record.offset());
        ack.acknowledge();
    }
}
