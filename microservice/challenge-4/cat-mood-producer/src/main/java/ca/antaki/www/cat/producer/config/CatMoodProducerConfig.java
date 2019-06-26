package ca.antaki.www.cat.producer.config;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jmx.JmxReporter;

import ca.antaki.www.cat.producer.handler.CatMoodHandler;
import ca.antaki.www.cat.producer.handler.ExecutorServiceMoodHandler;
import ca.antaki.www.cat.producer.handler.MoodGeneratorAndSender;
import ca.antaki.www.cat.producer.handler.StreamsMoodHandler;

@Configuration
public class CatMoodProducerConfig {
	
	private static final Logger LOG = LoggerFactory.getLogger(CatMoodProducerConfig.class);

	@Value("${kafka.server.address}")
	private String kafkaAddress;

	@Value("${topic.name}")
	private String topic;
	
	@Value("${threads:1}")
	private int threads;
	
	@Value("${cats:100}")
	private int nbCats;
	
	@Value("${change.mood.interval.seconds:27}")
	private int changeMoodIntervalSeconds;
	
	@Value("${executor.implementation:stream}")
	private String executorImplementation;
	
	
	@PostConstruct
	public void logConfig() {
		LOG.info("kafkaAddress = {}", kafkaAddress);
		LOG.info("topic = {}", topic);
		LOG.info("nbCats = {}", nbCats);
		LOG.info("threads = {}", threads);
		LOG.info("change.mood.interval.seconds = {}", changeMoodIntervalSeconds);
	}
	@Bean
	public ProducerFactory<String, String> producerFactory() {
		Map<String, Object> configProps = new HashMap<>();
		
		
		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaAddress);
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configProps.put(ProducerConfig.CLIENT_ID_CONFIG, "cat-mood-producer");
		return new DefaultKafkaProducerFactory<>(configProps);
	}

	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
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
	
	@Bean
	public CatMoodHandler catMoodHandler(MetricRegistry metricRegistry, MoodGeneratorAndSender moodGeneratorAndSender) {
		if("stream".equalsIgnoreCase(executorImplementation)) {
			LOG.info("Using StreamsMoodHandler");
			return new StreamsMoodHandler(metricRegistry, moodGeneratorAndSender);
		}
		LOG.info("Using ExecutorServiceMoodHandler");
		return new ExecutorServiceMoodHandler(metricRegistry, moodGeneratorAndSender, threads);
	}
	

	
	
	public String getKafkaAddress() {
		return kafkaAddress;
	}
	
	public void setKafkaAddress(String kafkaAddress) {
		this.kafkaAddress = kafkaAddress;
	}
	
	public String getTopic() {
		return topic;
	}
	
	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	public int getThreads() {
		return threads;
	}
	
	public void setThreads(int threads) {
		this.threads = threads;
	}
	
	public int getNbCats() {
		return nbCats;
	}
	
	public void setNbCats(int nbCats) {
		this.nbCats = nbCats;
	}
	
	public int getChangeMoodIntervalSeconds() {
		return changeMoodIntervalSeconds;
	}
	
}
