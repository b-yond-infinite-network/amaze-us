package com.challenge.suitecrm.ingester;

import static org.assertj.core.api.Assertions.assertThat;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import org.junit.Test;

public class DataTypeConfigurationTest {

    @Test
    public void create_from_file() throws URISyntaxException {
        Config config = ConfigFactory.load();
        final URI config_file_path = getClass().getClassLoader().getResource("application.conf").toURI();
        final Map<Path, DataTypeConfiguration>
            dataTypeConfiguration = DataTypeConfiguration.from(Paths.get(config_file_path).getParent(), config);

        assertThat(dataTypeConfiguration.values()).usingFieldByFieldElementComparator().containsExactlyInAnyOrder(

            new DataTypeConfiguration("CUSTOMERS", "ingest.*\\.csv", "customer", "customers-stream"),
            new DataTypeConfiguration("ORDERS", "ingest.*\\.csv", "order", "orders-stream"),
            new DataTypeConfiguration("PRODUCTS", "ingest.*\\.csv", "product", "products-stream"),
            new DataTypeConfiguration("PRODUCTS_RELATIONS", "ingest.*\\.csv", "products_relations", "products-relations-stream")
        );
    }
}
