package com.optumrx.ms.mdm.messaging;

import com.optumrx.ms.mdm.messaging.consumer.KafkaConsumerExample;
import com.optumrx.ms.mdm.messaging.file.ProviderFileLoader;
import com.optumrx.ms.mdm.messaging.file.ProviderFileWatcher;
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
        String path = "/home/wwu1000/test/preferred_alt_esb.sql";
	    ConfigurableApplicationContext context = SpringApplication.run(MdmMessagingApplication.class, args);
		//context.getBean(KafkaProducerExample.class).runProducer(5);
        //context.getBean(KafkaConsumerExample.class).runConsumer(5);
        //context.getBean(ProviderFileLoader.class).processProviderFile("C:\\Software\\documents\\RxLink.notes");
        //context.getBean(ProviderFileLoader.class).processProviderFile("H:\\db_backup\\preferred_alt_esb.sql");
        if (args[0] != null) {
            path = args[0];
        }
        context.getBean(ProviderFileWatcher.class).watchProviderFiles(path);
	}

}
