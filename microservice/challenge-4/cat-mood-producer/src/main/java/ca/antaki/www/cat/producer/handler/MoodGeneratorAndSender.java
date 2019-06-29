package ca.antaki.www.cat.producer.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;

import ca.antaki.www.cat.producer.business.MoodGenerator;
import ca.antaki.www.cat.producer.kafka.KafkaSender;
import ca.antaki.www.cat.producer.model.Cat;

@Component
public class MoodGeneratorAndSender {
	private static final Logger LOG = LoggerFactory.getLogger(MoodGeneratorAndSender.class);
	
	private KafkaSender sender;
	private final MoodGenerator moodGenerator;
	
	@Autowired
	public MoodGeneratorAndSender(MoodGenerator moodGenerator, KafkaSender sender) {
		this.moodGenerator = moodGenerator;
		this.sender = sender;
	}

	public final void generateAndSend(Cat cat) {
		cat.setMood(moodGenerator.generateMood());
		if (LOG.isDebugEnabled()) {
			LOG.debug("t={} c={}", Thread.currentThread().getName(), cat);
		}

		//use gson it's faster than Jackson, I don't want to use useless objects for no reason
		//TODO carl use Avro for ex

		sender.sendMessage(convertToJson(cat, System.currentTimeMillis()));
	}

	static String convertToJson(Cat cat, long time) {
		JsonObject json = new JsonObject();
		json.addProperty("name", cat.getName());
		json.addProperty("mood", cat.getMood());
		json.addProperty("time", time);
		return json.toString().toString();
	}
}
