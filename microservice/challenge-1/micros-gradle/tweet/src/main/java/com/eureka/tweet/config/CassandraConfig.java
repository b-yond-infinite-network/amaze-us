package com.eureka.tweet.config;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.DefaultNamingStrategy;
import com.datastax.driver.mapping.DefaultPropertyMapper;
import com.datastax.driver.mapping.MappingConfiguration;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.PropertyMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.datastax.driver.core.schemabuilder.SchemaBuilder.createKeyspace;
import static com.datastax.driver.mapping.NamingConventions.LOWER_CAMEL_CASE;
import static com.datastax.driver.mapping.NamingConventions.LOWER_SNAKE_CASE;


/**
 * Cassandra config.
 *
 * @author Miguel Gonzalez (maggonzz@gmail.com)
 * @since 0.0.1
 */
@Configuration
public class CassandraConfig {

    @Bean
    public Cluster cluster(
            @Value("${cassandra.host:127.0.0.1}") String host,
            @Value("${cassandra.cluster.name:cluster}") String clusterName,
            @Value("${cassandra.port:9042}") int port) {
        return Cluster.builder()
                .addContactPoint(host)
                .withPort(port)
                .withClusterName(clusterName)
                .withCredentials("cassandra", "cassandra")
                .withoutJMXReporting()
                .withoutMetrics().build();
    }

    @Bean
    public Session session(Cluster cluster, @Value("${cassandra.keyspace:tweets}") String keyspace)
            throws IOException {
        final Session session = cluster.connect();
        setupKeyspace(session, keyspace);
        return session;
    }

    private void setupKeyspace(Session session, String keyspace) throws IOException {
        final Map<String, Object> replication = new HashMap<>();
        replication.put("class", "SimpleStrategy");
        replication.put("replication_factor", 1);
        session.execute(createKeyspace(keyspace).ifNotExists().with().replication(replication));
        session.execute("USE " + keyspace);
        //    String[] statements = split(IOUtils.toString(getClass().getResourceAsStream("/cql/setup.cql")), ";");
        //    Arrays.stream(statements).map(statement -> normalizeSpace(statement) + ";").forEach(session::execute);
    }

    @Bean
    public MappingManager mappingManager(Session session) {
        final PropertyMapper propertyMapper =
                new DefaultPropertyMapper()
                        .setNamingStrategy(new DefaultNamingStrategy(LOWER_CAMEL_CASE, LOWER_SNAKE_CASE));
        final MappingConfiguration configuration =
                MappingConfiguration.builder().withPropertyMapper(propertyMapper).build();
        return new MappingManager(session, configuration);
    }
}
