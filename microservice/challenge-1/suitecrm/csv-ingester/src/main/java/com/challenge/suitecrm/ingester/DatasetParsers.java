package com.challenge.suitecrm.ingester;

import com.challenge.suitecrm.ingester.commons.DatasetParser;
import com.challenge.suitecrm.ingester.customers.RawCustomerDatasetParser;
import com.challenge.suitecrm.ingester.customers.RawOrdersDatasetParser;
import com.challenge.suitecrm.ingester.products.RawProductDatasetParser;
import com.challenge.suitecrm.ingester.products.RawProductRelationshipDatasetParser;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class DatasetParsers {

    private final static Map<String, DatasetParser<?, ?>> PARSERS = new HashMap<>();

    static {
        // List of Parsers to use
        register(new RawCustomerDatasetParser());
        register(new RawOrdersDatasetParser());
        register(new RawProductDatasetParser());
        register(new RawProductRelationshipDatasetParser());

    }

    public static void register(final DatasetParser<?, ?> parser){
        PARSERS.put(parser.streamName,parser);
    }

    public static Optional<DatasetParser<?, ?>> of(final String streamName){
        return Optional.ofNullable(PARSERS.get(streamName));
    }

}
