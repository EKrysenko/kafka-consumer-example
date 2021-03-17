package com.elcompanies.eventhubkafkaconsumer.config;

import com.elcompanies.eventhubkafkaconsumer.model.AbstractConsumerMessage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Bean
    @ConditionalOnMissingBean(name = "kafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, AbstractConsumerMessage> kafkaListenerContainerFactory(
            ConsumerFactory<String, AbstractConsumerMessage> kafkaConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, AbstractConsumerMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        factory.setConsumerFactory(kafkaConsumerFactory);
        return factory;
    }
}
