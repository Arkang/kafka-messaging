package demo.kafka.messaging.file;

import demo.kafka.messaging.domain.Message;
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
    private KafkaTemplate<String, Message> kafkaTemplate;

    private static final Logger logger = LogManager.getLogger(ProviderKafkaProducer.class);


    public void runProducer(final long index, final String msg) {
        Message message = new Message();
        message.setContent(msg);
        ListenableFuture<SendResult<String, Message>> future = kafkaTemplate.send(topic, 0, String.valueOf(index), message);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Message>>() {
            @Override
            public void onSuccess(SendResult<String, Message> result) {
                logger.info("Sent message to topic: " + result.getRecordMetadata().topic() + " with message: " + message);
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.info("Failed to send message");
            }
        });
    }

}
