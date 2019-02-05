package com.challenge.suitecrm.ingester.products;

import com.challenge.suitecrm.datamodel.ingester.ProductRelationShip;
import com.challenge.suitecrm.datamodel.utils.KeyProduct;
import com.challenge.suitecrm.ingester.commons.DatasetParser;
import org.beanio.builder.CsvParserBuilder;
import org.beanio.builder.FieldBuilder;
import org.beanio.builder.RecordBuilder;
import org.beanio.builder.StreamBuilder;

/**
 * Csv datasset parser for products relationship
 */
public class RawProductRelationshipDatasetParser extends DatasetParser<KeyProduct, ProductRelationShip> {

    public final static String STREAM_NAME = "products_relations";

    @Override protected StreamBuilder createStreamBuilder(Class<ProductRelationShip> recordType) {
        return new StreamBuilder(STREAM_NAME)
            .format("delimited")
            .parser(new CsvParserBuilder().delimiter('|').enableMultiline().allowUnquotedQuotes().quote('\\'))
            .addRecord(new RecordBuilder(STREAM_NAME, recordType)
                .addField(new FieldBuilder("productId").required())
                .addField(new FieldBuilder("targetProductId").required())
                .addField(new FieldBuilder("type").required())
            );
    }

  /**
   * get the kafka message key to apply
     * @param record
   * @return
   */
    @Override protected KeyProduct getKeyFromSpecificType(ProductRelationShip record) {
        return KeyProduct.newBuilder()
            .setProductId(record.getProductId())
            .build();
    }

    public RawProductRelationshipDatasetParser() {
        super(STREAM_NAME);
    }
}


