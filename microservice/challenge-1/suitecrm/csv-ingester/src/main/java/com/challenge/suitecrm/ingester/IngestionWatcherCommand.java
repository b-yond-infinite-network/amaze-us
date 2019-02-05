package com.challenge.suitecrm.ingester;

import static com.challenge.suitecrm.ingester.IngestionWatcherCommand.IngestionWatcherCommandOptions.OPTION_D;

import com.challenge.suitecrm.ingester.commons.DatasetProcessor;
import com.challenge.suitecrm.ingester.commons.RecordsProducer;
import com.challenge.suitecrm.ingester.commons.RejectionsHandler;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Run command: java -jar csv-ingester.jar -d < dataset path >
 */
@Slf4j
public class IngestionWatcherCommand {

    private static IngesterConfiguration ingesterConfiguration;

    static {
        ingesterConfiguration = new IngesterConfiguration();
    }

    public static void main(String[] args) {
        new IngestionWatcherCommand().run(args, new SystemExiter.Default(), System.out, ingesterConfiguration);
    }

    void run(String[] args, SystemExiter systemExiter, PrintStream out, IngesterConfiguration ingesterConfiguration) {
        final IngestionWatcherCommandOptions opts = new IngestionWatcherCommandOptions(args, out);

        if (opts.cmd.hasOption(OPTION_D)) {
            runDirectoryWatcher(opts, systemExiter, ingesterConfiguration);
        }
        systemExiter.exit(0);
    }

    void runDirectoryWatcher(IngestionWatcherCommandOptions opts, SystemExiter systemExiter, IngesterConfiguration ingesterConfiguration) {
        IngestionWatcherService ingestionWatcherService = null;
        try (RecordsProducer producer =
                new RecordsProducer.Kafka(ingesterConfiguration.producerConfiguration.kafkaProducerProperties);
        ){
            DatasetProcessor datasetProcessor = new DatasetProcessor(producer, new RejectionsHandler.Logger());
            ingestionWatcherService = new IngestionWatcherService(Paths.get(opts.rootWatchingPath),
                DataTypeConfiguration.from(Paths.get(opts.rootWatchingPath),
                    ingesterConfiguration.rawConfig), datasetProcessor);
            ingestionWatcherService.run();
        } catch (IOException ex) {
            log.error("failed to scan directory {}, failed.", opts.rootWatchingPath, ex);
            systemExiter.exit(1);
        } catch (Exception e) {
            log.error("Ingestion watcher of directory {}, failed.", opts.rootWatchingPath, e);
            systemExiter.exit(1);
        } finally {
            closeQuietly(ingestionWatcherService);
        }
    }

    private void closeQuietly(IngestionWatcherService ingestionWatcherService) {
        if (ingestionWatcherService != null) {
            try {
                ingestionWatcherService.close();
            } catch (Exception e) {
                log.error("Cannot close ingestion watcher, failed.", e);
            }
        }
    }

    static class IngestionWatcherCommandOptions {

        static final String OPTION_D = "d";

        private final CommandLineParser parser;
        private final CommandLine cmd;
        private final Options options = new Options();

        {
            options.addRequiredOption(OPTION_D,"directory",true,"directory path to scan");
        }

        String rootWatchingPath;

        IngestionWatcherCommandOptions(String[] args, PrintStream out) {
            parser = new DefaultParser();
            try {
                cmd = parser.parse(options, args);
                rootWatchingPath = cmd.getOptionValue(OPTION_D);
            } catch (ParseException e) {
                final HelpFormatter helpFormatter = new HelpFormatter();
                PrintWriter pw = new PrintWriter(out);
                helpFormatter.printHelp(pw, helpFormatter.getWidth(), "ingestionWatcher", null, options, helpFormatter.getLeftPadding(), helpFormatter
                    .getDescPadding(), null, true);
                pw.flush();
                throw new IllegalArgumentException(e);
            }
        }
    }
}
