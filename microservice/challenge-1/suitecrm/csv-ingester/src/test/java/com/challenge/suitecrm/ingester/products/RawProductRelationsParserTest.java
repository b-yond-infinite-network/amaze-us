package com.challenge.suitecrm.ingester.products;

import static org.assertj.core.api.Assertions.assertThat;

import com.challenge.suitecrm.datamodel.ingester.ProductRelationShip;
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

public class RawProductRelationsParserTest {

    private RawProductRelationshipDatasetParser parser = new RawProductRelationshipDatasetParser();

    @Test
    public void must_be_registred(){
        final Optional<DatasetParser<?, ?>> parser = DatasetParsers.of(RawProductRelationshipDatasetParser.STREAM_NAME);
        assertThat(parser.isPresent()).isTrue();
        assertThat(parser.get()).isInstanceOf(RawProductRelationshipDatasetParser.class);
    }

    @Test
    public void must_parse_from_file() {
        InputStream resourceAsStream =
            RawProductRelationshipDatasetParser.class.getResourceAsStream(
                "/datasets/_ingester_samples/PRODUCTS_RELATIONS/ingest_products_relations.csv");

        Stream<Either<RecordError, ProductRelationShip>> parsedStream = parser.parse(resourceAsStream);

        List<ProductRelationShip> actual = parsedStream.map(occ -> occ.right().value()).collect(Collectors.toList());
        assertThat(actual)
            .hasSize(13);
    }

    @Test
    public void must_parse_occurences(){

        String raw_lines =
            "1|3|SUBSTITUE";

        List<ProductRelationShip> expected = ImmutableList.of(
            ProductRelationShip.newBuilder()
                .setProductId("1")
                .setTargetProductId("3")
                .setType("SUBSTITUE")
                .build()
        );

        assertThat(ProductRelationShip.getClassSchema()).isEqualTo(parser.recordAvroSchema);

        final Stream<Either<RecordError,ProductRelationShip>> stream = parser.parse(new ByteArrayInputStream(raw_lines.getBytes(StandardCharsets.UTF_8)));
        final List<ProductRelationShip> actual = stream.map(
            x -> x.right().value()
        ).collect(Collectors.toList());

        assertThat(
            actual.stream()
                .map(v->v.getType())
                .collect(Collectors.toList())
        ).isEqualTo(
            expected.stream()
                .map(v->v.getType())
                .collect(Collectors.toList()));
    }
}
