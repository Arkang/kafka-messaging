package demo.kafka.messaging;

import demo.kafka.messaging.file.ProviderFileWatcher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@ImportResource("classpath:applicationContext.xml")
public class MdmMessagingApplication {

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(MdmMessagingApplication.class, args);
        //context.getBean(KafkaProducerExample.class).runProducer(5);
        //context.getBean(KafkaConsumerExample.class).runConsumer(5);
        //context.getBean(ProviderFileLoader.class).processProviderFile("C:\\Software\\documents\\RxLink.notes");
        //context.getBean(ProviderFileLoader.class).processProviderFile("H:\\db_backup\\preferred_alt_esb.sql");
        context.getBean(ProviderFileWatcher.class).watchProviderFiles();
    }

}
