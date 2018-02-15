package com.optumrx.ms.mdm.messaging.config;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.concurrent.CountDownLatch;

public class Listener {

    public final CountDownLatch countDownLatch1 = new CountDownLatch(1);

    private static final Logger logger = LogManager.getLogger(Listener.class);

    @KafkaListener(id = "${kafka.consumer.id}", topics = "${kafka.consumer.topic}", group = "${kafka.consumer.group}")
    public void listen(ConsumerRecord<?, ?> record) {
        logger.info("Received: " + record);
        countDownLatch1.countDown();
    }

}
