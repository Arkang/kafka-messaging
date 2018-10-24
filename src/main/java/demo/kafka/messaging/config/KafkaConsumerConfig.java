package demo.kafka.messaging.config;

import demo.kafka.messaging.domain.Message;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Value("${kafka.consumer.server}")
    private String server;
    @Value(("${kafka.consumer.group}"))
    private String group;
    @Value(("${kafka.consumer.autocommit}"))
    private boolean autoCommit;
    @Value(("${kafka.consumer.autocommit.interval.ms}"))
    private int autoCommitInterval;
    @Value(("${kafka.consumer.session.timeout.ms}"))
    private int sessionTimeout;
    @Value(("${kafka.consumer.poll.timeout.ms}"))
    private int pollTimeout;
    @Value("${kafka.consumer.auto.offset.reset}")
    private String autoOffsetReset;
    @Value("${kafka.consumer.key.serializer}")
    private String keySerializer;
    @Value("${kafka.consumer.value.serializer}")
    private String valueSerializer;

    @Bean("kafkaListenerContainerFactory")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Message>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Message> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(1);
        factory.getContainerProperties().setPollTimeout(pollTimeout);
        return factory;
    }

    @Bean("kafkaListenerContainerFactory1")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Message>> kafkaListenerContainerFactory1() {
        ConcurrentKafkaListenerContainerFactory<String, Message> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory1());
        factory.setConcurrency(1);
        factory.getContainerProperties().setPollTimeout(pollTimeout);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, Message> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public ConsumerFactory<String, Message> consumerFactory1() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    public Map<String, Object> consumerConfigs() {
        Map<String, Object> propsMap = new HashMap<>();
        propsMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
        propsMap.put(ConsumerConfig.GROUP_ID_CONFIG, group);
        //propsMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        propsMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, autoCommit);
        propsMap.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitInterval);
        propsMap.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeout);
        propsMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keySerializer);
        propsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueSerializer);
        propsMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        return propsMap;
    }

}
