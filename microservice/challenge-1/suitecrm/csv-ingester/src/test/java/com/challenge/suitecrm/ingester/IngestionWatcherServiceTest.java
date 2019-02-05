package com.challenge.suitecrm.ingester;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import com.challenge.suitecrm.ingester.commons.DatasetProcessor;
import com.challenge.suitecrm.test.CommonGenerator;
import com.google.common.base.Throwables;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class IngestionWatcherServiceTest {

    final DatasetProcessor datasetProcessor = mock(DatasetProcessor.class);

    @Test
    public void must_watch_at_one_folder() throws IOException {
        final Path rootWatchingPath = createRootDirectory();
        final Path dir1 = Files.createTempDirectory(rootWatchingPath,"level1");
        final Path dir2 = Files.createTempDirectory(dir1,"level2");
        final Path dir3 = Files.createTempDirectory(dir2,"level3");

        Map<Path, DataTypeConfiguration> config = new HashMap<>();
        config.put(dir2, new DataTypeConfiguration("level2",".*","customer","topic1"));

        final IngestionWatcherService ingestionWatcherService = new IngestionWatcherService(rootWatchingPath, config, datasetProcessor);

        assertThat(ingestionWatcherService.watchablePath(rootWatchingPath)).isFalse();
        assertThat(ingestionWatcherService.watchablePath(dir1)).isFalse();
        assertThat(ingestionWatcherService.watchablePath(dir2)).isTrue();
        assertThat(ingestionWatcherService.watchablePath(dir3)).isFalse();
    }

    @Test(expected = RuntimeException.class)
    public void must_throw_exeception() throws IOException {
        final Path rootWatchingPath = createRootDirectory();

        Map<Path, DataTypeConfiguration> config = new HashMap<>();
        config.put(Paths.get("path_unknown"), new DataTypeConfiguration("level2",".*","customer","topic1"));

        new IngestionWatcherService(rootWatchingPath, config, datasetProcessor);
    }

    private Path createRootDirectory(){
        try {
            return Files.createTempDirectory("dir-" + CommonGenerator.nextInt());
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }
}
