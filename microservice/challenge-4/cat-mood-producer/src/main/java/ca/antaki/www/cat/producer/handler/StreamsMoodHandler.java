package ca.antaki.www.cat.producer.handler;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
	
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.codahale.metrics.Timer.Context;

import ca.antaki.www.cat.producer.model.Cat;

public class StreamsMoodHandler implements CatMoodHandler{
	private static final Logger LOG = LoggerFactory.getLogger(StreamsMoodHandler.class);
	
	private final Timer changeMoodTimer;
	private MoodGeneratorAndSender moodGeneratorAndSender;

	
	@Autowired
	public StreamsMoodHandler(MetricRegistry metricRegistry, MoodGeneratorAndSender moodGeneratorAndSender) {
		this.changeMoodTimer = metricRegistry.timer("changeMoodTime");
		this.moodGeneratorAndSender = moodGeneratorAndSender;
	}

	@Override
	public void handle(List<Cat> cats) {
		Context ctx = changeMoodTimer.time();
		cats.parallelStream().forEach(cat -> {
			moodGeneratorAndSender.generateAndSend(cat);
		});
		
		long time = ctx.stop();
		LOG.info("changeMood.streams.time.ms = {} ms", TimeUnit.NANOSECONDS.toMillis(time));
		
	}
	
}