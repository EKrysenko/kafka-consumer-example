package com.elcompanies.eventhubkafkaconsumer.config;

import com.elcompanies.eventhubkafkaconsumer.model.ConsumerMessage;
import com.google.common.primitives.Ints;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;

import java.util.Arrays;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Bean
    @ConditionalOnMissingBean(name = "kafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, ConsumerMessage> kafkaListenerContainerFactory(
            ConsumerFactory<String, ConsumerMessage> kafkaConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, ConsumerMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        factory.setConsumerFactory(kafkaConsumerFactory);
        // filter messages by custom header
        factory.setRecordFilterStrategy(record -> 13 != record.value().getProcessAtMinutes());
//        factory.setRecordFilterStrategy(recordFilterStrategy());
        return factory;
    }

    public RecordFilterStrategy<String, ConsumerMessage> recordFilterStrategy() {
        return consumerRecord -> Arrays.stream(consumerRecord.headers().toArray())
                .anyMatch(header ->
                        "filter".equals(header.key())
                                && Ints.fromByteArray(header.value()) != 14);
    }
}
