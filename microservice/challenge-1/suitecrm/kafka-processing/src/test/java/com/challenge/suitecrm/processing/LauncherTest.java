package com.challenge.suitecrm.processing;

import static org.assertj.core.api.Assertions.assertThat;

import com.challenge.suitecrm.processing.commons.TopologyBuilder;
import com.challenge.suitecrm.processing.topology.CustomersTopologyBuilder;
import com.typesafe.config.ConfigFactory;
import java.util.Map;
import org.junit.Test;

public class LauncherTest {

    @Test
    public void topology_launcher() {
        Launcher launcher = new Launcher(ConfigFactory.load(), CustomersTopologyBuilder.TOPOLOGY);
         Map<String, TopologyBuilder> topolgies = launcher.initTopolgy();

        assertThat(topolgies).containsOnlyKeys(
            CustomersTopologyBuilder.TOPOLOGY
        );
    }

    @Test
    public void no_topology_launcher() {
        Launcher launcher = new Launcher(ConfigFactory.load(), "");
        Map<String, TopologyBuilder> topologies = launcher.initTopolgy();

        assertThat(topologies).isEmpty();
    }

    @Test(expected = IllegalArgumentException.class)
    public void topology_not_found() {
        Launcher launcher = new Launcher(ConfigFactory.load(), "toto.xxx, toto.zzz.yyy");
        launcher.initTopolgy();
    }

}
