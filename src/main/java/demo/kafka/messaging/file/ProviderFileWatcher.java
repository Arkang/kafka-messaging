package demo.kafka.messaging.file;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;

@Component
public class ProviderFileWatcher {

    private static final String INCOMING = "incoming";
    private static final String PROCESSING = "processing";
    private static final String ARCHIVED = "archived";

    @Value("${kafka.producer.incoming.path}")
    private String watchPath;

    @Autowired
    private ProviderFileLoader fileLoader;

    private static final Logger logger = LogManager.getLogger(ProviderFileWatcher.class);

    public void watchProviderFiles() {
        Path watchDir = Paths.get(watchPath);
        // processing existing incoming files
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(watchPath))) {
            for (Path path : directoryStream) {
                processProviderFile(path);
            }
        } catch (Exception ex) {
            logger.error("Error with directory listing.", ex);
        }
        while (true) {
            // processing new incoming files
            try {
                WatchService watcher = FileSystems.getDefault().newWatchService();
                watchDir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);
                WatchKey watchKey;

                try {
                    watchKey = watcher.take();
                } catch (InterruptedException ex) {
                    logger.error("Error when retrieving a watch key.", ex);
                    return;
                }
                for (WatchEvent<?> event : watchKey.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();
                    if (kind == StandardWatchEventKinds.OVERFLOW) {
                        continue;
                    }
                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    Path filename = ev.context();
                    logger.info("Event kind:" + event.kind() + ". File affected: " + event.context() + ".");
                    Path incomingFile = watchDir.resolve(filename);
                    processProviderFile(incomingFile);
                }
                watchKey.reset();
            } catch (Exception ex) {
                logger.error("Error with directory watching at : " + watchDir, ex);
            }
        }
    }

    private void processProviderFile(Path incomingFile) throws Exception {
        Path processFile = moveFile(incomingFile, PROCESSING, true);
        fileLoader.processProviderFile(processFile);
        moveFile(processFile, ARCHIVED, false);
        logger.info("Completed loading " + incomingFile.getFileName() + " to Kafka");
    }

    private Path moveFile(Path fileToMove, String target, boolean appendTimestamps) throws IOException {
        Path twoLevelParentPath = fileToMove.getParent().getParent();
        Path targetPath = twoLevelParentPath.resolve(target);
        Path targetFile = null;
        if (appendTimestamps) {
            String timestamps = "-" + System.currentTimeMillis();
            targetFile = targetPath.resolve(fileToMove.getFileName() + timestamps);
        } else {
            targetFile = targetPath.resolve(fileToMove.getFileName());
        }
        Files.move(fileToMove, targetFile);
        return targetFile;
    }

}
