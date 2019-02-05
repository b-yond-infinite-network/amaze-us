package com.challenge.suitecrm.ingester.commons;

import fj.data.Either;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;
import org.apache.avro.specific.SpecificRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatasetProcessor {

    private final RecordsProducer recordsProducer;
    private final RejectionsHandler rejectionsHandler;

    public DatasetProcessor(final RecordsProducer recordsProducer, final RejectionsHandler rejectionsHandler) {
        this.recordsProducer = recordsProducer;
        this.rejectionsHandler = rejectionsHandler;
    }

    public Report process(final DatasetParser<? extends SpecificRecord, ? extends SpecificRecord> datasetParser
            , final InputStream inputStream
            , final String topic
            , final String fileName){

        final Report report = new Report(fileName);
        final Stream<? extends Either<DatasetParser.RecordError, ? extends SpecificRecord>> stream = datasetParser.parse(fileName, inputStream);
        stream.forEach(
            occurence -> {
                if (occurence.isRight()) {
                    final SpecificRecord record = occurence.right().value();

                    recordsProducer.send(topic, datasetParser.getKey(record), record);
                    report.incrementInjections();
                } else {
                    rejectionsHandler.discard(occurence.left().value());
                    report.incrementRejections();
                }
                report.report();
            }
        );
        return report;
    }

    public static class Report {
        private final static Logger LOGGER = LoggerFactory.getLogger(Report.class);
        private final int reportingInterval = 100;

        private final AtomicLong injected;
        private final AtomicLong rejected;
        private final String fileName;

        public Report(String fileName, long injected, long rejected) {
            this.fileName = fileName;
            this.injected = new AtomicLong(injected);
            this.rejected = new AtomicLong(rejected);
        }

        public Report(String fileName) {
            this(fileName,0l,0l);
        }

        public long processed(){
            return injected.get() + rejected.get();
        }

        public void incrementInjections(){
            injected.incrementAndGet();
        }

        public void incrementRejections(){
            rejected.incrementAndGet();
        }

        public void report(){
            if (processed() % reportingInterval == 0){
                LOGGER.debug("Intermediary processing :" + this.toString());
            }
        }

        public boolean hasRejects() {
            return rejected.get() > 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Report report = (Report) o;

            if (injected != null ? !injected.equals(report.injected) : report.injected != null) return false;
            return rejected != null ? rejected.equals(report.rejected) : report.rejected == null;
        }

        @Override
        public int hashCode() {
            int result = injected != null ? injected.hashCode() : 0;
            result = 31 * result + (rejected != null ? rejected.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder(" { ");
            sb.append("fileName=").append(fileName);
            sb.append(", processed=").append(processed());
            sb.append(", injected=").append(injected.get());
            sb.append(", rejected=").append(rejected.get());
            sb.append('}');
            return sb.toString();
        }
    }

}
