package demo.kafka.messaging.file;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class ProviderFileLoader {

    @Autowired
    private ProviderKafkaProducer kafkaProducer;

    private static final Logger logger = LogManager.getLogger(ProviderFileLoader.class);

    public void processProviderFile(Path filePath) throws Exception {
        try (BufferedReader bufferedReader = Files.newBufferedReader(filePath, Charset.forName("UTF-8"))) {
            String currentLine = null;
            long index = 0L;
            while ((currentLine = bufferedReader.readLine()) != null) {
                logger.info("message to be sent to Kafka: " + currentLine);
                index++;
                kafkaProducer.runProducer(index, currentLine);
            }
        } catch (FileNotFoundException fnfe) {
            logger.error("Can't find drug alternative files", fnfe);
        } catch (IOException ioe) {
            logger.error("Failed to process drug alternative files", ioe);
        }
    }

}
