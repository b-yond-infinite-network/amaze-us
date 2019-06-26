package ca.antaki.www.cat.mood.consumer.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.codahale.metrics.Timer.Context;
import com.google.gson.Gson;

import ca.antaki.www.cat.mood.consumer.dao.CatMoodDao;

@KafkaListener(topics = "cats", groupId = "cat-consumer")
@Component
public class CatMoodConsumerListener {
	private static final Logger LOG = LoggerFactory.getLogger(CatMoodConsumerListener.class);
	
	private final Timer consumeTimer;
	
	private final CatMoodDao catMoodDao;
	private ThreadLocal<Gson> threadLocalGson = new ThreadLocal<>() {
		public Gson get() {
			return new Gson();
		};
	};
	
	
	public CatMoodConsumerListener(CatMoodDao catMoodDao, MetricRegistry metricRegistry) {
		this.catMoodDao = catMoodDao;
		this.consumeTimer = metricRegistry.timer("consume.time");
	}
	
	@KafkaHandler
	public void listen(List<String> messages) {
		Context ctx = consumeTimer.time();
		final int size = messages.size();
		List<CatMood> catMoods = new ArrayList<>(size);
		if(LOG.isDebugEnabled()) LOG.debug("Received a batch of {} messages", size);
		messages.forEach( message -> {
			CatMood catMood = threadLocalGson.get().fromJson(message, CatMood.class);
			catMoods.add(catMood);
		});

		
		catMoodDao.insertCatMoods(catMoods);
		long time = ctx.stop();
		LOG.info("Total time consume {} messages is {}",size, TimeUnit.NANOSECONDS.toMillis(time));
	}
	
	public CatMoodDao getCatMoodDao() {
		return catMoodDao;
	}
	
	
	
}
