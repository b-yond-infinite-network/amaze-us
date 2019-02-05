package com.challenge.suitecrm.ingester.customers;

import static org.assertj.core.api.Assertions.assertThat;

import com.challenge.suitecrm.datamodel.storage.Order;
import com.challenge.suitecrm.ingester.DatasetParsers;
import com.challenge.suitecrm.ingester.commons.DatasetParser;
import com.challenge.suitecrm.ingester.commons.DatasetParser.RecordError;
import com.google.common.collect.ImmutableList;
import fj.data.Either;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Test;

public class RawOrdersParserTest {

    private RawOrdersDatasetParser parser = new RawOrdersDatasetParser();

    @Test
    public void must_be_registred(){
        final Optional<DatasetParser<?, ?>> parser = DatasetParsers.of(RawOrdersDatasetParser.STREAM_NAME);
        assertThat(parser.isPresent()).isTrue();
        assertThat(parser.get()).isInstanceOf(RawOrdersDatasetParser.class);
    }

    @Test
    public void must_parse_from_file() {
        InputStream resourceAsStream =
            RawOrdersDatasetParser.class.getResourceAsStream(
                "/datasets/_ingester_samples/ORDERS/ingest_orders.csv");

        Stream<Either<RecordError, Order>> parsedStream = parser.parse(resourceAsStream);

        List<Order> actual = parsedStream.map(occ -> occ.right().value()).collect(Collectors.toList());
        assertThat(actual)
            .hasSize(2);
    }

    @Test
    public void must_parse_occurences(){

        String raw_lines =
            "1|1|1|Macbook Pro 2019|COMPUTER|3000";

        List<Order> expected = ImmutableList.of(
            Order.newBuilder()
                .setCustomerId("1")
                .setOrderId("1")
                .setProductId("1")
                .build()
        );

        assertThat(Order.getClassSchema()).isEqualTo(parser.recordAvroSchema);

        final Stream<Either<RecordError,Order>> stream = parser.parse(new ByteArrayInputStream(raw_lines.getBytes(StandardCharsets.UTF_8)));
        final List<Order> actual = stream.map(
            x -> x.right().value()
        ).collect(Collectors.toList());

        assertThat(
            actual.stream()
                .map(v->v.getOrderId())
                .collect(Collectors.toList())
        ).isEqualTo(
            expected.stream()
                .map(v->v.getOrderId())
                .collect(Collectors.toList()));
    }
}
