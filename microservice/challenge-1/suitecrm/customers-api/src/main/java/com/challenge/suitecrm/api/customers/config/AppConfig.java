package com.challenge.suitecrm.api.customers.config;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.LocalDate;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.TypeCodec;
import com.datastax.driver.extras.codecs.MappingCodec;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class AppConfig {

    private final Environment env;


    @Autowired
    public AppConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public Session cassandraConnection() {
        Cluster cluster = Cluster
            .builder()
            .withoutMetrics()
            .addContactPoints(env.getProperty("cassandra.contactpoints").split(","))
            .withPort(Integer.parseInt(env.getProperty("cassandra.port")))
            .build();
        cluster.getConfiguration().getCodecRegistry().register(
            new LocalDateAsLocalDateDatastax(),
            new DateAsOffsetDateTime()
        );

        return cluster.connect(env.getProperty("cassandra.keyspace"));
    }

    private class LocalDateAsLocalDateDatastax extends
        MappingCodec<java.time.LocalDate, LocalDate> {

        LocalDateAsLocalDateDatastax() {
            super(TypeCodec.date(), java.time.LocalDate.class);
        }

        @Override protected java.time.LocalDate deserialize(LocalDate value) {
            return value != null ? java.time.LocalDate.ofEpochDay(value.getDaysSinceEpoch()) : null;
        }

        @Override protected LocalDate serialize(java.time.LocalDate value) {
            return null;
        }
    }

    private class DateAsOffsetDateTime extends MappingCodec<OffsetDateTime, Date> {

        DateAsOffsetDateTime() {
            super(TypeCodec.timestamp(), OffsetDateTime.class);
        }

        @Override protected OffsetDateTime deserialize(Date value) {
            return value != null ? OffsetDateTime.ofInstant(value.toInstant(), ZoneId.systemDefault()) : null;
        }

        @Override protected Date serialize(OffsetDateTime value) {
            return null;
        }
    }

}
