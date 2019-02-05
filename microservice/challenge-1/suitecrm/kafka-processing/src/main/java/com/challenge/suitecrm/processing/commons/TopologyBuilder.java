package com.challenge.suitecrm.processing.commons;

import org.apache.kafka.streams.Topology;

public interface TopologyBuilder<T extends BaseConfiguration> {

    Topology buildStreamingTopology();

    T getConfiguration();

}
