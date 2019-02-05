package com.challenge.suitecrm.test;

import com.datastax.driver.core.Session;
import info.archinnov.achilles.embedded.CassandraEmbeddedServerBuilder;
import info.archinnov.achilles.embedded.CassandraShutDownHook;
import info.archinnov.achilles.embedded.PortFinder;
import info.archinnov.achilles.type.TypedMap;
import java.util.List;

public class EmbeddedCassandra {

    public final static Session start(int port, String keyspace, List<String> scripts) {
        TypedMap parameters = new TypedMap();

        CassandraShutDownHook shutdownHook = new CassandraShutDownHook();
        CassandraEmbeddedServerBuilder builder =
            CassandraEmbeddedServerBuilder.builder().withShutdownHook(shutdownHook).withCQLPort(port).withKeyspaceName(keyspace)
                .cleanDataFilesAtStartup(true);

        builder.withParams(parameters);
        scripts.forEach(builder::withScript);

        return builder.buildNativeSession();
    }

    public final static Session start(String keyspace, List<String> scripts) {
        return start(PortFinder.findAvailableBetween(9043, 9142), keyspace, scripts);
    }
}
