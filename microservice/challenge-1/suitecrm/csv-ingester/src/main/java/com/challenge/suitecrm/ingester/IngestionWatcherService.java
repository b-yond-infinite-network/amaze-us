package com.challenge.suitecrm.ingester;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

import com.challenge.suitecrm.ingester.commons.DatasetProcessor;
import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.BufferOverflowException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IngestionWatcherService implements Runnable, AutoCloseable {

    private static final int WAIT_BEFORE_LOCK_FILE_IN_MILLIS = 250;

    private static final String DONE = "_ingester_DONE";
    private static final String ERROR = "_ingester_ERROR";

    private final WatchService watcher = FileSystems.getDefault().newWatchService();
    private final Map<WatchKey, Path> keys = Maps.newHashMap();
    private final Map<Path, DataTypeConfiguration> configs;
    private final Path rootWatchingDir;
    private final Path doneDir;
    private final Path errorDir;
    private final AtomicBoolean running = new AtomicBoolean(true);
    private final boolean sensitiveMode;
    private final DatasetProcessor datasetProcessor;


    private IngestionWatcherService(final Path rootWatchingPath, Map<Path,DataTypeConfiguration> configs, DatasetProcessor datasetProcessor, boolean sensitiveMode) throws IOException {
        this.rootWatchingDir = rootWatchingPath;
        this.sensitiveMode = sensitiveMode;
        this.datasetProcessor = datasetProcessor;
        this.configs = configs;
        this.doneDir = Files.createDirectories(rootWatchingDir.resolve(DONE));
        this.errorDir = Files.createDirectories(rootWatchingDir.resolve(ERROR));
        Files.walkFileTree(rootWatchingDir, new SimpleFileVisitor<Path>() {
            @Override public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                if (watchablePath(dir)) {
                    keys.put(dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY), dir);
                }
                return FileVisitResult.CONTINUE;
            }
        });

        List<Path> pathStream = configs.keySet().stream().filter(x -> !keys.values().contains(x)).collect(Collectors.toList());
        if (!pathStream.isEmpty()) {
            throw new RuntimeException("Configuration failed => path " + pathStream + " not found");
        }
    }

    IngestionWatcherService(final Path rootWatchingDir, Map<Path,DataTypeConfiguration> configs, DatasetProcessor datasetProcessor) throws IOException {
        this(rootWatchingDir, configs, datasetProcessor,false);
    }

    public void run()  {
        log.info("Start watcher service with root path : {}", rootWatchingDir.toAbsolutePath());
        log.info("Initially registered directories : {}", keys.values().toString());
        log.info("Initially configured : {}", configs != null ? configs.values().stream().map(x -> Arrays.asList(x.path, x.filenamePattern, x.streamName, x.targetTopic)).collect(Collectors.toSet()) : null);

        while (running.get()){
            WatchKey key;
            try {
                key = watcher.take();
            } catch (InterruptedException x) {
                log.error("Unexpected error occured", x);
                break;
            }
            Path directory = keys.get(key);
            if (directory == null) {
                continue;
            }
            for (WatchEvent<?> event: key.pollEvents()) {
                WatchEvent.Kind kind = event.kind();

                if (kind == OVERFLOW) {
                    throw new BufferOverflowException();// Aly: bad handling, but okey for this showcase
                }

                WatchEvent<Path> ev = (WatchEvent<Path>) event;
                Path name = ev.context();
                Path path = directory.resolve(name);

                if (kind == ENTRY_CREATE) {
                    // nothing to do
                    log.info("ENTRY_CREATE: Detects created on path {}", path.toFile());
                    handleCreateEntry(path);
                } else if (kind == ENTRY_MODIFY) {
                    log.info("ENTRY_MODIFY: Detects on path {}", path.toFile());
                    handleCreateEntry(path);
                } else if (kind == ENTRY_DELETE) {
                    log.info("ENTRY_DELETE: Detects deleted on path {}", path.toFile());
                    // nothing to do
                }

                if (!key.reset()){
                    break;
                }
            }
        }
    }

    private void handleCreateEntry(Path dataPath) {
        

        log.info("dataPath : "+ dataPath);
        
        if (checkFile(dataPath)) {
            log.info("Data file {} detected and put in processing buffer.", dataPath);
            try {
                DatasetProcessor.Report report = processDatafile(dataPath);
                if (report != null && report.hasRejects()) {
                    moveFileIntoFinishDirectory(dataPath, errorDir.toString());
                } else {
                    moveFileIntoFinishDirectory(dataPath, doneDir.toString());
                }
            } catch (IOException e) {
                log.error("Ingesting of file {} failed.", dataPath.toString(), e);
                if (sensitiveMode) {
                    throw Throwables.propagate(e);
                }
            }
        } else {
            log.warn("Detected file/directory {} is unrecognized. ", dataPath.toString());
            if (sensitiveMode) {
                throw new IllegalStateException(String.format("Detected file/directory %s is unrecognized. ", dataPath.toString()));
            }
        }
    }

    private void moveFileIntoFinishDirectory(Path dataPath, String finishDirectory) throws IOException {
        Path doneDataFile= Paths.get(dataPath.toString().replace(rootWatchingDir.toString(), finishDirectory)
            .concat("-" + LocalDateTime.now().format(ISO_LOCAL_DATE_TIME).replaceAll(":", "-")));
        doneDataFile.toFile().getParentFile().mkdirs();
        Files.move(dataPath, doneDataFile);

    }


    private DatasetProcessor.Report processDatafile(Path dataPath) throws IOException {
        final DataTypeConfiguration dataTypeConfiguration = configs.get(dataPath.getParent());

        boolean matches = Pattern
            .compile(dataTypeConfiguration.filenamePattern, Pattern.CASE_INSENSITIVE)
            .matcher(dataPath.getFileName().toString()).matches();
        if (matches){
            FileLock dataLock = waitToFileLock(dataPath);

            try (final InputStream inputStream = Channels.newInputStream(dataLock.channel())) {
                log.info("Starting ingestion file {} of stream {} into topic {}", dataPath.toString(),dataTypeConfiguration.streamName,dataTypeConfiguration.targetTopic);
                
                final DatasetProcessor.Report report = datasetProcessor
                    .process(DatasetParsers.of(dataTypeConfiguration.streamName).get(), inputStream, dataTypeConfiguration.targetTopic, dataPath.toFile().getName());

                log.info("Ingesting file {} of stream {} into topic {}, finished: {}"
                    , dataPath.toString(), dataTypeConfiguration.streamName, dataTypeConfiguration.targetTopic, report.toString());
                log.info("lock released on file on {}, finished.", dataPath.toString());
                dataLock.release();
                return report;
            }
        } else {
            log.error("Detected data file {} do not respect naming pattern {}.", dataPath.toString(), dataTypeConfiguration.filenamePattern);
            if (sensitiveMode){
                throw new IllegalStateException(String.format("Detected data file %s do not respect naming pattern %s.", dataPath.toString(), dataTypeConfiguration.filenamePattern));
            } else {
                DatasetProcessor.Report report = new DatasetProcessor.Report(dataPath.toString());
                report.incrementRejections();
                return report;
            }
        }
    }



    private FileLock waitToFileLock(Path path) throws IOException {
        log.info("Waiting file lock on {}", path.toString());
        File lockFile = path.toFile();
        FileChannel channel = new RandomAccessFile( lockFile, "rw" ).getChannel( );
        FileLock lock = null;
        while (lock == null) {
            // Could not get the lock
            try {
                Thread.sleep(WAIT_BEFORE_LOCK_FILE_IN_MILLIS);
            } catch (InterruptedException e) {
                throw new IOException(String.format("Waiting file lock on %s, failed.", path.toString()), e);
            }
            try {
                lock = channel.tryLock();
            } catch (OverlappingFileLockException e) {
                // File is already locked in this thread or virtual machine
                // Retry to lock
            }
        }
        // Got the lock
        log.info("Waiting file lock on {}, finished.", path.toString());
        return lock;
    }

    boolean watchablePath(final Path path){
        for (Path key : configs.keySet()) {
            if (Files.isDirectory(path, NOFOLLOW_LINKS) && path.equals(key)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkFile(Path dataPath){
        final DataTypeConfiguration dataTypeConfiguration = configs.get(dataPath.getParent());
            return isDatafile(dataPath);
    }
    private boolean isDatafile(final Path path){
        return Files.isReadable(path) && (! Files.isDirectory(path));
    }

    public Map<Path, DataTypeConfiguration> getConfiguration() {
        return configs;
    }

    public void stop(){
        this.running.set(false);
    }

    @Override
    public void close() throws Exception {
        watcher.close();
    }
}

