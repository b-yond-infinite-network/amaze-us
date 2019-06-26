package ca.antaki.www.cat.producer.handler;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.codahale.metrics.Timer.Context;

import ca.antaki.www.cat.producer.model.Cat;

public class ExecutorServiceMoodHandler implements CatMoodHandler {
	private static final Logger LOG = LoggerFactory.getLogger(ExecutorServiceMoodHandler.class);
	
	private MoodGeneratorAndSender moodGeneratorAndSender;
	private final Timer changeMoodTimer;
	private ExecutorService executorService;
	private final int nbThreads;
	
	@Autowired
	public ExecutorServiceMoodHandler(MetricRegistry metricRegistry, 
			MoodGeneratorAndSender moodGeneratorAndSender, int threads) {
		this.changeMoodTimer = metricRegistry.timer("changeMoodTime");
		this.moodGeneratorAndSender = moodGeneratorAndSender;
		this.nbThreads = threads;
		executorService = Executors.newFixedThreadPool(nbThreads);
		LOG.info("Created a threadpool with {} threads", nbThreads);
	}

	@Override
	public void handle(List<Cat> cats) {
		Context ctx = changeMoodTimer.time();
		CountDownLatch latch = new CountDownLatch(cats.size());
		for (Cat cat : cats) {
			executorService.submit(() -> {
				moodGeneratorAndSender.generateAndSend(cat);
			
				latch.countDown();
			});
		}
		try {
			latch.await(1, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			LOG.warn("InterruptedException in changeMood", e);
			throw new RuntimeException(e);
		} finally {
			long time = ctx.stop();
			LOG.info("changeMood.time.ms = {} ms", TimeUnit.NANOSECONDS.toMillis(time));
		}
	}

}
