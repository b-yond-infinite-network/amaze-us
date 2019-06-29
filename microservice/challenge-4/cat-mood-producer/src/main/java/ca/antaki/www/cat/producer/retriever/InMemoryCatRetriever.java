package ca.antaki.www.cat.producer.retriever;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.codahale.metrics.Timer.Context;

import ca.antaki.www.cat.producer.model.Cat;

@Component
public class InMemoryCatRetriever implements CatRetriever {
	private static final Logger LOG = LoggerFactory.getLogger(InMemoryCatRetriever.class);

	private final Timer generateCatsTimer;
	
	@Autowired
	public InMemoryCatRetriever(MetricRegistry registry) {
		this.generateCatsTimer = registry.timer("generate.cats.timer");
	}
	
	@Override
	public List<Cat> retrieve(int amount) {
		Context ctx = generateCatsTimer.time();
		List<Cat> list = new ArrayList<>(amount);
		int nameCount = 0;
		for(int i = 0;i < amount;i++) {
			list.add(new Cat("cat"+ (++nameCount)));
		}
		long time = ctx.stop();
		LOG.info("generate.cats.time.ms = {}", TimeUnit.NANOSECONDS.toMillis(time));
		return list;
	}

}
