package demo.kafka.messaging.consumer;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Properties;

@Service
public class KafkaConsumerExample {

    private final static String TOPIC = "kafka-poc-topic";
    private final static String BOOTSTRAP_SERVERS = "apsrt3484:9092";

    private static Consumer<Long, String> createConsumer() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "KafkaPocConsumer0");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 10000);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                LongDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class.getName());
        return new KafkaConsumer<>(props);
    }

    public static void runConsumer(final int numOfPolls) throws Exception {
        final Consumer<Long, String> consumer = createConsumer();
        consumer.subscribe(Arrays.asList(TOPIC));
        try {
            //for ( int i = 0; i < numOfPolls; i ++) {
            while (true) {
                long time = System.currentTimeMillis();
                final ConsumerRecords<Long, String> records = consumer.poll(2000);
                long elapsedTime = System.currentTimeMillis() - time;
                System.out.printf("pulled records: %d\n", records.count());
                System.out.printf("time elapsed: %d\n", elapsedTime);
                for (ConsumerRecord record : records) {
                    System.out.printf("received record(offset=%s value=%s)\n",
                            record.offset(), record.value());
                }
            }
        } finally {
            consumer.commitAsync();
            consumer.close();
        }
    }
}
