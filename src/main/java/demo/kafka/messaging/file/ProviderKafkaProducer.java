package demo.kafka.messaging.file;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class ProviderKafkaProducer {

    @Value(("${kafka.producer.topic}"))
    private String topic;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final Logger logger = LogManager.getLogger(ProviderKafkaProducer.class);


    public void runProducer(final long index, final String message) {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, 0, String.valueOf(index), message);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                logger.info("Sent message to topic: " + result.getRecordMetadata().topic() + " with message: " + message);
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.info("Failed to send message");
            }
        });
    }

}
