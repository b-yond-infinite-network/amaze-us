package com.challenge.suitecrm.ingester.customers;

import com.challenge.suitecrm.datamodel.ingester.RawCustomer;
import com.challenge.suitecrm.datamodel.utils.KeyCustomer;
import com.challenge.suitecrm.ingester.commons.DatasetParser;
import org.beanio.builder.CsvParserBuilder;
import org.beanio.builder.FieldBuilder;
import org.beanio.builder.RecordBuilder;
import org.beanio.builder.StreamBuilder;
import org.beanio.types.BooleanTypeHandler;

/**
 * Csv datasset parser for products
 */
public class RawCustomerDatasetParser extends DatasetParser<KeyCustomer, RawCustomer> {

    public final static String STREAM_NAME = "customer";

    @Override protected StreamBuilder createStreamBuilder(Class<RawCustomer> recordType) {
        return new StreamBuilder(STREAM_NAME)
            .format("delimited")
            .parser(new CsvParserBuilder().delimiter('|').enableMultiline().allowUnquotedQuotes().quote('\\'))
            .addRecord(new RecordBuilder(STREAM_NAME, recordType)
                .addField(new FieldBuilder("customerId").required())
                .addField(new FieldBuilder("fullName"))
                .addField(new FieldBuilder("address"))
                .addField(new FieldBuilder("phone"))
                .addField(new FieldBuilder("email"))
                .addField(new FieldBuilder("stopEmail").typeHandler(new BooleanTypeHandler()))
                .addField(new FieldBuilder("stopPhone").typeHandler(new BooleanTypeHandler()))
            );
    }

    /**
     * get the kafka message key to apply
     * @param record
     * @return
     */
    @Override protected KeyCustomer getKeyFromSpecificType(RawCustomer record) {
        return KeyCustomer.newBuilder()
            .setCustomerId(record.getCustomerId())
            .build();
    }

    public RawCustomerDatasetParser() {
        super(STREAM_NAME);
    }
}


