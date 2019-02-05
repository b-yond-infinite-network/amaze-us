package com.challenge.suitecrm.ingester;

import com.google.common.base.Preconditions;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValue;
import java.util.Map;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IngesterConfiguration {


    public final Config rawConfig;

    public final KafkaRecordsProducerConfiguration  producerConfiguration;

    public final boolean logOnStart;

    public IngesterConfiguration(Config rawConfig) {
        this.rawConfig = rawConfig;
        this.logOnStart = this.rawConfig.getBoolean("log.on.start");
        if(logOnStart){
           log.info("Effective configuration : {} {} ",System.lineSeparator(),this.rawConfig.root().render());
        }
        Preconditions.checkArgument(this.rawConfig.hasPath("producer"),"configuration should contain a 'producer' block.");
        this.producerConfiguration = this.new KafkaRecordsProducerConfiguration();
    }

    public IngesterConfiguration() {
        this(ConfigFactory.load());
    }


    public class KafkaRecordsProducerConfiguration{

        private final Config rawConfig = IngesterConfiguration.this.rawConfig.getConfig("producer");

        public final Properties kafkaProducerProperties = asProperties();

        public KafkaRecordsProducerConfiguration() {
            Preconditions.checkArgument(this.rawConfig.hasPath("kafka"), "configuration should contain a 'producer.kafka' block.");
        }

        private final Properties asProperties(){
            final Properties properties = new Properties();
            final Config kafkaConfig = this.rawConfig.getConfig("kafka");
            for (final Map.Entry<String, ConfigValue> entry: kafkaConfig.entrySet()){
                properties.put(entry.getKey(),entry.getValue().unwrapped().toString());
            }
            return properties;
        }

    }

}
