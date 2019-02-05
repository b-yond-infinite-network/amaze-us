package com.challenge.suitecrm.ingester;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.challenge.suitecrm.datamodel.ingester.RawCustomer;
import com.challenge.suitecrm.datamodel.utils.KeyCustomer;
import com.challenge.suitecrm.ingester.commons.DatasetParser;
import com.challenge.suitecrm.ingester.commons.DatasetProcessor;
import com.challenge.suitecrm.ingester.commons.RecordsProducer;
import com.challenge.suitecrm.ingester.commons.RejectionsHandler;
import com.challenge.suitecrm.ingester.customers.RawCustomerDatasetParser;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import org.apache.avro.specific.SpecificRecord;
import org.junit.Test;


public class DatasetProcessorTest {

    final RecordsProducer producer = mock(RecordsProducer.class);
    final RejectionsHandler rejectionsHandler = mock(RejectionsHandler.class);
    final DatasetParser<? extends SpecificRecord, ? extends SpecificRecord> datasetParser = DatasetParsers
        .of(
        RawCustomerDatasetParser.STREAM_NAME).get();
    final DatasetProcessor datasetProcessor = new DatasetProcessor(producer,rejectionsHandler);

    @Test
    public void consume_stream(){
        String raw_lines =  "1|toto|my address Cergy France|0601010101|toto@gmail.com||\n" +
                "|titi|my address Cergy France|0601010101|titi@gmail.com||\n";

        final RawCustomer customer = RawCustomer.newBuilder()
            .setCustomerId("1")
            .setFullName("toto")
            .setAddress("my address Cergy France")
            .setPhone("0601010101")
            .setEmail("toto@gmail.com")
            .build();

        final DatasetParser.RecordError rejection = new DatasetParser.RecordError("customer",
            "",
            2,
            raw_lines,
            ImmutableMap.of("customerId", ImmutableList.of("Required field not set"))
        );

        datasetProcessor.process(
            datasetParser,
            new ByteArrayInputStream(raw_lines.getBytes(StandardCharsets.UTF_8)),
            "topic",
            "monFichier.txt");

        verify(producer,times(1))
            .send(
                eq("topic"),
                eq(KeyCustomer.newBuilder().setCustomerId("1").build()),
                eq(customer)
            );
        verify(rejectionsHandler,never())
            .discard(eq(rejection));
    }

    @Test
    public void do_nothing_on_empty_stream(){
        datasetProcessor.process(
            datasetParser,
            new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8)),
            "topic",
            "myFile.txt");

        verify(producer,never()).send(anyString(),any(SpecificRecord.class),any(SpecificRecord.class));
        verify(rejectionsHandler,never()).discard(any(DatasetParser.RecordError.class));
    }
}

