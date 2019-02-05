package com.challenge.suitecrm.ingester.products;

import static org.assertj.core.api.Assertions.assertThat;

import com.challenge.suitecrm.datamodel.ingester.RawProduct;
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

public class RawProductParserTest {

    private RawProductDatasetParser parser = new RawProductDatasetParser();

    @Test
    public void must_be_registred(){
        final Optional<DatasetParser<?, ?>> parser = DatasetParsers.of(RawProductDatasetParser.STREAM_NAME);
        assertThat(parser.isPresent()).isTrue();
        assertThat(parser.get()).isInstanceOf(RawProductDatasetParser.class);
    }

    @Test
    public void must_parse_from_file() {
        InputStream resourceAsStream =
            RawProductDatasetParser.class.getResourceAsStream(
                "/datasets/_ingester_samples/PRODUCTS/ingest_products.csv");

        Stream<Either<RecordError, RawProduct>> parsedStream = parser.parse(resourceAsStream);

        List<RawProduct> actual = parsedStream.map(occ -> occ.right().value()).collect(Collectors.toList());
        assertThat(actual)
            .hasSize(10);
    }

    @Test
    public void must_parse_occurences(){

        String raw_lines =
            "1|Macbook Pro 2019|COMPUTER|3000";

        List<RawProduct> expected = ImmutableList.of(
            RawProduct.newBuilder()
                .setProductId("1")
                .setDescription("Macbook Pro 2019")
                .build()
        );

        assertThat(RawProduct.getClassSchema()).isEqualTo(parser.recordAvroSchema);

        final Stream<Either<RecordError,RawProduct>> stream = parser.parse(new ByteArrayInputStream(raw_lines.getBytes(StandardCharsets.UTF_8)));
        final List<RawProduct> actual = stream.map(
            x -> x.right().value()
        ).collect(Collectors.toList());

        assertThat(
            actual.stream()
                .map(v->v.getDescription())
                .collect(Collectors.toList())
        ).isEqualTo(
            expected.stream()
                .map(v->v.getDescription())
                .collect(Collectors.toList()));
    }
}
