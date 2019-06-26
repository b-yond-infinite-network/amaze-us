package ca.antaki.www.cat.producer.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import ca.antaki.www.cat.producer.config.CatMoodProducerConfig;

@Component
public class KafkaSender {

	private KafkaTemplate<String, String> kafkaTemplate;
	
	private final String topicName;
	
	@Autowired
	public KafkaSender(CatMoodProducerConfig config, KafkaTemplate<String, String> kafkaTemplate) {
		this.topicName = config.getTopic();
		this.kafkaTemplate = kafkaTemplate;
	}
	 
	public void sendMessage(String msg) {
	    kafkaTemplate.send(topicName, msg);
	}
	
	public KafkaTemplate<String, String> getKafkaTemplate() {
		return kafkaTemplate;
	}
	
	
}
