package ca.antaki.www.cat.producer.handler;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;

import ca.antaki.www.cat.producer.model.Cat;

@RunWith(MockitoJUnitRunner.class)
public class StreamsMoodHandlerTest {
	private StreamsMoodHandler testee;
	
	@Mock
	private MetricRegistry metricRegistry;

	
	@Mock
	private MoodGeneratorAndSender mockMoodGeneratorAndSender;

	@Before
	public void setUp() {
		Timer timer = new Timer();
		when(metricRegistry.timer("changeMoodTime")).thenReturn(timer);
		this.testee = new StreamsMoodHandler(metricRegistry, mockMoodGeneratorAndSender);
	}

	@Test
	public void testHandle() {
		Cat cat1 = new Cat("cat1");
		Cat cat2 = new Cat("cat2");
		
		List<Cat> cats = List.of(cat1, cat2);
		testee.handle(cats);

		verify(mockMoodGeneratorAndSender, times(1)).generateAndSend(cat1);
		verify(mockMoodGeneratorAndSender, times(1)).generateAndSend(cat2);

	}

}
