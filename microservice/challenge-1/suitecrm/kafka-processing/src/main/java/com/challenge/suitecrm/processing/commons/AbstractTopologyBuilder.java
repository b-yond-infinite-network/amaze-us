package com.challenge.suitecrm.processing.commons;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.KeyValueStore;

public abstract class AbstractTopologyBuilder<T extends BaseConfiguration> implements TopologyBuilder {

    protected final T configuration;

    protected AbstractTopologyBuilder(BaseConfiguration configuration) {
        this.configuration = (T) configuration;
    }

    public T getConfiguration() {
        return configuration;
    }

    protected <K, V> Materialized<K, V, KeyValueStore<Bytes, byte[]>> getMaterialized(
        Pair<String, Boolean> stateStore) {
        if (stateStore.getValue()) {
            return Materialized.<K, V, KeyValueStore<Bytes, byte[]>> as(stateStore.getKey()).withCachingDisabled();
        }
        return Materialized.as(stateStore.getKey());
    }
}
