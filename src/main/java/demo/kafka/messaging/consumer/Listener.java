package demo.kafka.messaging.consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class Listener {

    public final CountDownLatch countDownLatch1 = new CountDownLatch(1);

    private static final Logger logger = LogManager.getLogger(Listener.class);

    @KafkaListener(id = "${kafka.consumer.id}",
            topicPartitions = { @TopicPartition(topic = "${kafka.consumer.topic}", partitions = { "0" }) },
            groupId = "${kafka.consumer.group}")
    public void listen(String record) {
        logger.info("Received: " + record);
        //countDownLatch1.countDown();
    }

}
