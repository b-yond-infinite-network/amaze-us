package ca.antaki.www.cat.producer.retriever;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import com.codahale.metrics.MetricRegistry;

import ca.antaki.www.cat.producer.model.Cat;

public class InMemoryCatRetrieverTest {
	
	private InMemoryCatRetriever testee;
	
	public InMemoryCatRetrieverTest() {
		MetricRegistry registry = new MetricRegistry();
		testee = new InMemoryCatRetriever(registry);
	}

	@Test
	public void test() {
		List<Cat> cats = testee.retrieve(2);
		assertNotNull(cats);
		assertEquals(2, cats.size());
		assertEquals(new Cat("cat1"), cats.get(0));
		assertEquals(new Cat("cat2"), cats.get(1));
		
	}

}
