package com.challenge.suitecrm.ingester.customers;

import com.challenge.suitecrm.datamodel.storage.Order;
import com.challenge.suitecrm.datamodel.utils.KeyOrder;
import com.challenge.suitecrm.ingester.commons.DatasetParser;
import org.beanio.builder.CsvParserBuilder;
import org.beanio.builder.FieldBuilder;
import org.beanio.builder.RecordBuilder;
import org.beanio.builder.StreamBuilder;
import org.beanio.types.DoubleTypeHandler;

/**
 * Csv datasset parser for customer's ratings
 */
public class RawOrdersDatasetParser extends DatasetParser<KeyOrder, Order> {

    public final static String STREAM_NAME = "order";

    @Override protected StreamBuilder createStreamBuilder(Class<Order> recordType) {
        return new StreamBuilder(STREAM_NAME)
            .format("delimited")
            .parser(new CsvParserBuilder().delimiter('|').enableMultiline().allowUnquotedQuotes().quote('\\'))
            .addRecord(new RecordBuilder(STREAM_NAME, recordType)
                .addField(new FieldBuilder("customerId").required())
                .addField(new FieldBuilder("orderId").required())
                .addField(new FieldBuilder("productId").required())
                .addField(new FieldBuilder("productDescription"))
                .addField(new FieldBuilder("productCategory"))
                .addField(new FieldBuilder("amount").typeHandler(new DoubleTypeHandler()))
            );
    }

  /**
   * get the kafka message key to apply
     * @param record
   * @return
   */
    @Override protected KeyOrder getKeyFromSpecificType(Order record) {
        return KeyOrder.newBuilder()
            .setCustomerId(record.getCustomerId())
            .setOrderId(record.getOrderId())
            .build();
    }

    public RawOrdersDatasetParser() {
        super(STREAM_NAME);
    }
}


