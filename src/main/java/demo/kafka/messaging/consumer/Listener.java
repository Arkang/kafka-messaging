package demo.kafka.messaging.consumer;

import demo.kafka.messaging.domain.Message;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class Listener {

    public final CountDownLatch countDownLatch1 = new CountDownLatch(1);

    private static final Logger logger = LogManager.getLogger(Listener.class);

    @KafkaListener(id = "${kafka.consumer.id}",
            containerFactory = "kafkaListenerContainerFactory",
            topicPartitions = { @TopicPartition(topic = "${kafka.consumer.topic}", partitions = { "0" }) },
            groupId = "${kafka.consumer.group}")
    public void listenToMessage(Message msg) {
        logger.info("Received: " + msg);
        //countDownLatch1.countDown();
    }

    @KafkaListener(id = "${kafka.consumer.id1}",
            containerFactory = "kafkaListenerContainerFactory1",
            topicPartitions = { @TopicPartition(topic = "${kafka.consumer.topic}", partitions = { "0" }) },
            groupId = "${kafka.consumer.group1}")
    public void listenToConsumerRecord(ConsumerRecord<?, ?> consumerRecord, Acknowledgment acknowledgment) {
        logger.info("Received: " + consumerRecord.value().toString());
        acknowledgment.acknowledge();
        //countDownLatch1.countDown();
    }

}
