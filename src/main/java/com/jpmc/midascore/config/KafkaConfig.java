package com.jpmc.midascore.config;

import com.jpmc.midascore.foundation.Transaction;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
// Import Value annotation
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    // Inject the bootstrap servers property managed by @EmbeddedKafka
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Transaction>>
    kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Transaction> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(3);
        factory.getContainerProperties().setPollTimeout(3000);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, Transaction> consumerFactory() {
        JsonDeserializer<Transaction> jsonDeserializer = new JsonDeserializer<>(Transaction.class, false);


        return new DefaultKafkaConsumerFactory<>(
                consumerConfigs(), // Pass the configuration map
                new StringDeserializer(),
                jsonDeserializer // Use the configured deserializer
        );
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        // Use the injected bootstrapServers value
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "my-group");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        // Deserializer props are configured in consumerFactory now,
        // but setting them here wouldn't hurt if preferred.
        System.out.println("Kafka consumer config initialized with injected bootstrap servers: " + bootstrapServers);
        return props;
    }
}