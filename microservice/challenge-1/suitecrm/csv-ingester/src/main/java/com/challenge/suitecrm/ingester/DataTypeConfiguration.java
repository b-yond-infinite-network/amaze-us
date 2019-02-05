package com.challenge.suitecrm.ingester;

import com.google.common.base.Preconditions;
import com.typesafe.config.Config;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataTypeConfiguration {

    final String path;
    final String filenamePattern;
    final String streamName;
    final String targetTopic;

    DataTypeConfiguration(String path, String filenamePattern, String streamName, String targetTopic) {
        Preconditions.checkArgument(DatasetParsers.of(streamName).isPresent(), "No defined parser for stream name %s", streamName);
        this.path = path;
        this.filenamePattern = filenamePattern;
        this.streamName = streamName;
        this.targetTopic = targetTopic;
    }

    public String getTargetTopic() {
        return targetTopic;
    }

    public static Map<Path, DataTypeConfiguration> from(final Path rootWatchingPath, final Config rawConfig){
        List<? extends Config> ingesterConfigs = rawConfig.getConfigList("ingester");
        if (ingesterConfigs != null) {
            Map<Path, DataTypeConfiguration> config = new HashMap<>();
            for (Config ingesterConfig : ingesterConfigs) {
                Config c = ingesterConfig;
                config.put(rootWatchingPath.resolve(c.getString("path")), new DataTypeConfiguration(
                    c.getString("path"),
                    c.getString("regex"),
                    c.getString("stream-name"),
                    c.getString("target-topic")));
            }
            return config;
        }
        return null;
    }

    @Override public String toString() {
        return "DataTypeConfiguration{" + "path='" + path + '\'' + ", filenamePattern='" + filenamePattern + '\'' + ", streamName='"
            + streamName + '\'' + ", targetTopic='" + targetTopic + '\'' + '}';
    }
}
