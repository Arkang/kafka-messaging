package com.optumrx.ms.mdm.messaging.file;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;

@Component
public class ProviderFileWatcher {

    private static final String INCOMING = "incoming";
    private static final String PROCESSING = "processing";
    private static final String ARCHIVED = "archived";

    @Autowired
    private ProviderFileLoader fileLoader;

    private static final Logger logger = LogManager.getLogger(ProviderFileWatcher.class);

    public void watchProviderFiles(String path) {
        Path watchDir = Paths.get(path);
        try {
            WatchService watcher = FileSystems.getDefault().newWatchService();
            watchDir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);
            WatchKey watchKey;
            while (true) {
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
                    Path processFile = moveFile(incomingFile, PROCESSING);
                    fileLoader.processProviderFile(processFile);
                    moveFile(processFile, ARCHIVED);
                    logger.info("Completed loading " + incomingFile.getFileName() + " to Kafka");
                }
                watchKey.reset();
            }
        } catch (Exception ex) {
            logger.error("Error with directory watching at : " + watchDir, ex);
        }
    }

    private Path moveFile(Path fileToMove, String target) throws IOException {
        Path parentPath = fileToMove.getParent();
        Path targetPath = parentPath.resolve(target);
        Files.move(fileToMove, targetPath.resolve(fileToMove.getFileName()));
        return targetPath.resolve(fileToMove.getFileName());
    }

}
