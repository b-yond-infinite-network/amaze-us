package com.challenge.suitecrm.ingester.products;

import com.challenge.suitecrm.datamodel.ingester.RawProduct;
import com.challenge.suitecrm.datamodel.utils.KeyProduct;
import com.challenge.suitecrm.ingester.commons.DatasetParser;
import org.beanio.builder.CsvParserBuilder;
import org.beanio.builder.FieldBuilder;
import org.beanio.builder.RecordBuilder;
import org.beanio.builder.StreamBuilder;
import org.beanio.types.DoubleTypeHandler;

/**
 * Csv datasset parser for products
 */
public class RawProductDatasetParser extends DatasetParser<KeyProduct, RawProduct> {

    public final static String STREAM_NAME = "product";

    @Override protected StreamBuilder createStreamBuilder(Class<RawProduct> recordType) {
        return new StreamBuilder(STREAM_NAME)
            .format("delimited")
            .parser(new CsvParserBuilder().delimiter('|').enableMultiline().allowUnquotedQuotes().quote('\\'))
            .addRecord(new RecordBuilder(STREAM_NAME, recordType)
                .addField(new FieldBuilder("productId").required())
                .addField(new FieldBuilder("description"))
                .addField(new FieldBuilder("category"))
                .addField(new FieldBuilder("price").typeHandler(new DoubleTypeHandler()))
            );
    }

  /**
   * get the kafka message key to apply
     * @param record
   * @return
   */
    @Override protected KeyProduct getKeyFromSpecificType(RawProduct record) {
        return KeyProduct.newBuilder()
            .setProductId(record.getProductId())
            .build();
    }

    public RawProductDatasetParser() {
        super(STREAM_NAME);
    }
}


