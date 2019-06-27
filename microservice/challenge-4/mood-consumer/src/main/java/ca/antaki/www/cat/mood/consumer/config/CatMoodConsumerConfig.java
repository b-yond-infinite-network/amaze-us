package ca.antaki.www.cat.mood.consumer.config;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jmx.JmxReporter;

@EnableKafka
@Configuration
public class CatMoodConsumerConfig {
	private static final Logger LOG = LoggerFactory.getLogger(CatMoodConsumerConfig.class);

	@Value("${kafka.server.address}")
	private String kafkaAddress;

	@Value("${kafka.group.id}")
	private String kafkaGroupId;
	
	@Value("${consumer.threads:10}")
	private int consumerThreads;
	
	@Value("${db.batch.size:50}")
	private int batchSize;

	@Bean
	public ConsumerFactory<String, String> consumerFactory() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaAddress);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaGroupId);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, batchSize);
		return new DefaultKafkaConsumerFactory<>(props);
	}
	
	@PostConstruct
	private void printConfig() {
		LOG.info("kafkaAddress= {}", kafkaAddress);
		LOG.info("kafkaGroupId= {}", kafkaGroupId);
		LOG.info("threads= {}", kafkaGroupId);
		LOG.info("db.batch.size= {}", batchSize);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {

		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		factory.setBatchListener(true);
		factory.setConcurrency(consumerThreads);
		return factory;
	}
	
	@Bean
	public MetricRegistry metricsRegistry() {
		return new MetricRegistry();
	}
	
	@Bean
	public JmxReporter jmxReporter(MetricRegistry registry) {
	    final JmxReporter reporter = JmxReporter.forRegistry(registry).build();
	    reporter.start();
	    return reporter;
	}
}