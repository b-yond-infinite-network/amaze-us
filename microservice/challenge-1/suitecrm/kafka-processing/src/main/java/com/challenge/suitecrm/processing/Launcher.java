package com.challenge.suitecrm.processing;

import static java.util.Arrays.asList;

import com.challenge.suitecrm.processing.commons.TopologyBuilder;
import com.challenge.suitecrm.processing.topology.CustomersTopologyBuilder;
import com.challenge.suitecrm.processing.topology.ProductsTopologyBuilder;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KafkaStreams;


@Slf4j
public class Launcher {

  private final List<String> topologyArgsFilter;


  private final Config rawConfig;

  public Launcher(Config rawConfig, String arg) {
    this.rawConfig = rawConfig;
    String[] topologies = arg.isEmpty() ? new String[0] : arg.split(",");
    this.topologyArgsFilter = asList(
        Arrays.stream(topologies).map(String::trim).toArray(String[]::new));
    log.info("Start launcher => {}", this.topologyArgsFilter);
  }

  public static void main(String[] args) {
    if (args != null && args.length > 0) {
      Config rawConfig = ConfigFactory.load();

      Launcher launcher = new Launcher(rawConfig, args[0]);
      Map<String, TopologyBuilder> topologies = launcher.initTopolgy();

      if (topologies.isEmpty()) {
        log.error("No topology to start");
      }

      launcher.startTopology(rawConfig, topologies);


    } else {
      log.error("No args - No topology to start");
    }
  }

  Map<String, TopologyBuilder> initTopolgy() {
    Map<String, Supplier<TopologyBuilder>> topologies = new HashMap<String, Supplier<TopologyBuilder>>() {{

      put(CustomersTopologyBuilder.TOPOLOGY,
          () -> new CustomersTopologyBuilder(rawConfig));

      put(ProductsTopologyBuilder.TOPOLOGY,
          () -> new ProductsTopologyBuilder(rawConfig));
    }};

    final List<String> typologyArgsNotFound = topologyArgsFilter.stream()
        .filter(s -> !topologies.containsKey(s)).collect(Collectors.toList());
    if (typologyArgsNotFound.size() > 0) {
      throw new IllegalArgumentException("topologie not found : " + typologyArgsNotFound);
    }

    return topologies.keySet()
        .stream()
        .filter(s -> topologyArgsFilter.contains(s.trim()))
        .collect(Collectors.toMap(o -> o, o -> topologies.get(o).get()));
  }

  private Map<String, KafkaStreams> startTopology(Config config,
      Map<String, TopologyBuilder> topologies) {
    Map<String, KafkaStreams> kafkaStreams = topologies.entrySet()
        .stream()
        .collect(Collectors.toMap(
            x -> x.getKey(),
            x -> new KafkaStreams(x.getValue().buildStreamingTopology(),
                x.getValue().getConfiguration().getStreamConfig())
        ));

    kafkaStreams.values().forEach(ks -> ks.setUncaughtExceptionHandler(
        (Thread thread, Throwable error) -> log.error("unhandled error!", error)));

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      log.info("Closing kafkaStreams application...");
      kafkaStreams.values().forEach(ks -> {
        ks.close(30, TimeUnit.SECONDS);
        ks.cleanUp();
        log.warn("KafkaStreams application closed => " + ks);
      });
      log.info("KafkaStreams application closed.");
    }));

    kafkaStreams.values().forEach(ks -> {
          ks.cleanUp();
          ks.start();
          log.info("KafkaStreams application started => " + ks);
        }
    );
    log.info("KafkaStreams application started.");

    return kafkaStreams;
  }
}
