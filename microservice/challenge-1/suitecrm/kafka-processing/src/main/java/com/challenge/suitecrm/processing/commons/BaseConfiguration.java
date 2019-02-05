package com.challenge.suitecrm.processing.commons;

import static com.google.common.collect.ImmutableMap.copyOf;

import com.google.common.collect.Maps;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigValue;
import java.util.Map;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.kafka.streams.StreamsConfig;

public abstract class BaseConfiguration {

    private final Config topologyConfig;

    private final StreamsConfig streamsConfig;

    public BaseConfiguration(Config config, String topology) {
        this.topologyConfig = config.getConfig("processing." + topology);
        this.streamsConfig = getStreamsConfig(config);
    }

    public StreamsConfig getStreamConfig() {
        return this.streamsConfig;
    }

    protected Pair<String, Boolean> getStateStore(String name) {
        Config store = topologyConfig.getObject("stores").toConfig().getObject(name).toConfig();
        return Pair.of(store.getString("name"), store.getBoolean("cacheDisabled"));
    }

    public String getOutputTopic(String name) {
        return topologyConfig.getObject("outputs").toConfig().getString(name);
    }

    public String getInputTopic(String name) {
        return topologyConfig.getObject("inputs").toConfig().getString(name);
    }

    protected String getStore(String name) {
        return topologyConfig.getObject("stores."+name).toConfig().getString("name");
    }

    private StreamsConfig getStreamsConfig(Config config) {
        return new StreamsConfig(asMap(
            CustomRocksDBConfig.class,
            config.getConfig("plateform"),
            config.getConfig("default.stream.config"),
            topologyConfig.getConfig("streams-config")));
    }

    private static Map<String, String> asMap(
        final Class<CustomRocksDBConfig> rocksDBConfig,
        final Config plateformConfig,
        final Config defaultConfig,
        final Config topologyConfig) {

        final Map<String, String> map = Maps.newHashMap();
        map.put(StreamsConfig.ROCKSDB_CONFIG_SETTER_CLASS_CONFIG, rocksDBConfig.getCanonicalName());
        for (final Map.Entry<String, ConfigValue> entry : plateformConfig.entrySet()) {
            map.put(entry.getKey(), entry.getValue().unwrapped().toString());
        }
        for (final Map.Entry<String, ConfigValue> entry : defaultConfig.entrySet()) {
            map.put(entry.getKey(), entry.getValue().unwrapped().toString());
        }
        for (final Map.Entry<String, ConfigValue> entry : topologyConfig.entrySet()) {
            map.put(entry.getKey(), entry.getValue().unwrapped().toString());
        }
        return copyOf(map);
    }
}
