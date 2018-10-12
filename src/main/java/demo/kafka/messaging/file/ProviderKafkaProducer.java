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
    //private final static String BOOTSTRAP_SERVERS = "apsrt3484:9092";

    //private KafkaProducer<Long, String> kafkaProducer;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final Logger logger = LogManager.getLogger(ProviderKafkaProducer.class);

/*    public ProviderKafkaProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaPocProducer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                LongSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        kafkaProducer = new KafkaProducer<>(props);
    }*/

    public void runProducer(final long index, final String message) throws Exception {
        //try {
/*            long time = System.currentTimeMillis();
            final ProducerRecord<Long, String> record = new ProducerRecord<>(TOPIC, index, message);
            RecordMetadata metadata = kafkaProducer.send(record).get();*/
            ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);
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
/*            long elapsedTime = System.currentTimeMillis() - time;
            logger.info("sent record(key=" + record.key() + " value=" + record.value() + " meta(partition="
                    + metadata.partition() + ", offset=" + metadata.offset() + ", time=" + elapsedTime);*/
        /*} finally {
            kafkaProducer.flush();
        }*/
    }

/*    public void closeProducer() {
        kafkaProducer.close();
    }*/
}
