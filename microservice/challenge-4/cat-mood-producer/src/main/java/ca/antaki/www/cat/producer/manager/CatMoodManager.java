package ca.antaki.www.cat.producer.manager;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.codahale.metrics.MetricRegistry;

import ca.antaki.www.cat.producer.config.CatMoodProducerConfig;
import ca.antaki.www.cat.producer.handler.CatMoodHandler;
import ca.antaki.www.cat.producer.model.Cat;
import ca.antaki.www.cat.producer.retriever.CatRetriever;

@Component
public class CatMoodManager implements CommandLineRunner {
	private static final Logger LOG = LoggerFactory.getLogger(CatMoodManager.class);

	private final CatRetriever catRetriever;
	
	private final int nbCats;
	private final int changeMoodIntervalSeconds;
	private ScheduledExecutorService scheduledExecutorService;

	private final CatMoodHandler catMoodHandler;


	private List<Cat> cats;

	@Autowired
	public CatMoodManager(CatRetriever catRetriever, CatMoodProducerConfig config,
			MetricRegistry metricRegistry, CatMoodHandler catMoodHandler) {
		this.catRetriever = catRetriever;
		this.nbCats = config.getNbCats();
		this.catMoodHandler = catMoodHandler;
		this.changeMoodIntervalSeconds = config.getChangeMoodIntervalSeconds();
	}

	@Override
	public void run(String... args) throws Exception {
		cats = Collections.unmodifiableList(catRetriever.retrieve(nbCats));
		scheduledExecutorService = Executors.newScheduledThreadPool(1);

		Runnable callable = () -> catMoodHandler.handle(cats);
		scheduledExecutorService.scheduleAtFixedRate(callable, 0, changeMoodIntervalSeconds, TimeUnit.SECONDS);
	}

	@PreDestroy
	public void shutdown() {
		LOG.info("Shutting down executor service");
		scheduledExecutorService.shutdown();
		try {
			scheduledExecutorService.awaitTermination(30, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			LOG.warn("InterruptedException in CatMoodManager.shutdown");
		}
	}

}
