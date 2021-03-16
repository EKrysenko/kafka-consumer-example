package com.elcompanies.eventhubkafkaconsumer.config;

import com.google.common.primitives.Ints;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;

import java.util.Arrays;

import static java.util.Base64.getEncoder;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Bean
    @ConditionalOnMissingBean(name = "kafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
            ConsumerFactory<Object, Object> kafkaConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        // filter messages by custom header
        factory.setRecordFilterStrategy(recordFilterStrategy());
        configurer.configure(factory, kafkaConsumerFactory);
        return factory;
    }

    public RecordFilterStrategy recordFilterStrategy() {
        return (RecordFilterStrategy) consumerRecord -> Arrays.stream(consumerRecord.headers().toArray())
                .anyMatch(header ->
                        "filter".equals(header.key())
                                && Ints.fromByteArray(header.value())!=14);
    }
}
