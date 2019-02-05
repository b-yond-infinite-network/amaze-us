package com.challenge.suitecrm.processing;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;

import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigObject;
import com.typesafe.config.ConfigValue;
import java.util.Set;
import org.junit.Test;

public class CheckConfiguration {

    public enum Zones {
      customers,
      stores
    }

    @Test
    public void checkConfiguration() {
        ConfigObject configs = ConfigFactory.load().getObject("processing");
        Set<String> zones = configs.keySet();
        assertThat(zones).isEqualTo(stream(Zones.values()).map(Enum::name).collect(toSet()));

        zones.forEach(
            zone -> {
                ConfigObject config = configs.toConfig().getObject(zone);
                config.keySet().forEach(
                    topology -> {
                        ConfigObject topologyConfig = config.toConfig().getObject(topology);
                        ConfigValue inputs = topologyConfig.get("inputs");
                        ConfigValue outputs = topologyConfig.get("outputs");
                        ConfigValue stores = topologyConfig.get("stores");

                        assertThat(inputs).as("no inputs in topology %s.%s", zone, topology).isNotNull();
                        if (stores == null) {
                            assertThat(outputs).as("no outputs in topology %s.%s", zone, topology).isNotNull();
                        }
                        if (outputs == null) {
                            assertThat(stores).as("no stores in topology %s.%s", zone, topology).isNotNull();
                        }
                    }
                );
            }
        );
    }
}
