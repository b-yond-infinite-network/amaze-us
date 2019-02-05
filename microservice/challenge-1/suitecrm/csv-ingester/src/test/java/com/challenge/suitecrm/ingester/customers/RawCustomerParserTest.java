package com.challenge.suitecrm.ingester.customers;

import static org.assertj.core.api.Assertions.assertThat;

import com.challenge.suitecrm.datamodel.ingester.RawCustomer;
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

public class RawCustomerParserTest {

    private RawCustomerDatasetParser parser = new RawCustomerDatasetParser();

    @Test
    public void must_be_registred(){
        final Optional<DatasetParser<?, ?>> parser = DatasetParsers.of(RawCustomerDatasetParser.STREAM_NAME);
        assertThat(parser.isPresent()).isTrue();
        assertThat(parser.get()).isInstanceOf(RawCustomerDatasetParser.class);
    }

    @Test
    public void must_parse_from_file() {
        InputStream resourceAsStream =
            RawCustomerDatasetParser.class.getResourceAsStream(
                "/datasets/_ingester_samples/CUSTOMERS/ingest_customers.csv");

        Stream<Either<RecordError, RawCustomer>> parsedStream = parser.parse(resourceAsStream);

        List<RawCustomer> actual = parsedStream.map(occ -> occ.right().value()).collect(Collectors.toList());
        assertThat(actual)
            .hasSize(2);
    }

    @Test
    public void must_parse_occurences(){

        String raw_lines =
            "2|John|address2 US|0101010101|john@test.com|false|false\n" +
            "1|Bob|address1 UK|0601010101|bob@test.com||true";

        List<RawCustomer> expected = ImmutableList.of(
            RawCustomer.newBuilder()
                .setCustomerId("2")
                .build(),
            RawCustomer.newBuilder()
                .setCustomerId("1")
                .build()
        );

        assertThat(RawCustomer.getClassSchema()).isEqualTo(parser.recordAvroSchema);

        final Stream<Either<DatasetParser.RecordError,RawCustomer>> stream = parser.parse(new ByteArrayInputStream(raw_lines.getBytes(StandardCharsets.UTF_8)));
        final List<RawCustomer> actual = stream.map(
            x -> x.right().value()
        ).collect(Collectors.toList());

        assertThat(
            actual.stream()
                .map(v->v.getCustomerId())
                .collect(Collectors.toList())
        ).isEqualTo(
            expected.stream()
                .map(v->v.getCustomerId())
                .collect(Collectors.toList()));
    }
}
