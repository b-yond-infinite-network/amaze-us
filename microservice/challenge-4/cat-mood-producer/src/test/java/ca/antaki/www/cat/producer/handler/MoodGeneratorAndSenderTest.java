package ca.antaki.www.cat.producer.handler;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.mockito.junit.MockitoJUnitRunner;

import ca.antaki.www.cat.producer.business.MoodGenerator;
import ca.antaki.www.cat.producer.kafka.KafkaSender;
import ca.antaki.www.cat.producer.model.Cat;

@RunWith(MockitoJUnitRunner.class)
public class MoodGeneratorAndSenderTest {
	
	private MoodGeneratorAndSender testee;
	
	@Mock
	private MoodGenerator mockMoodGenerator;
	
	@Mock
	private KafkaSender mockKafkaSender;
	
	@Before
	public void setUp() {
		this.testee = new MoodGeneratorAndSender(mockMoodGenerator, mockKafkaSender);
	}
	

	@Test
	public void test() {
		Cat cat = new Cat("a");
		cat.setMood((byte)1);
		String result = MoodGeneratorAndSender.convertToJson(cat, 1000l);
		assertEquals("{\"name\":\"a\",\"mood\":1,\"time\":1000}", result);
	}
	
	@Test
	public void testGenerateAndSend() {
		Cat cat = new Cat("cat");
		when(mockMoodGenerator.generateMood()).thenReturn((byte)1);
		testee.generateAndSend(cat);
		InOrder inOrder = Mockito.inOrder(mockMoodGenerator, mockKafkaSender);

		inOrder.verify(mockMoodGenerator, times(1)).generateMood();
		inOrder.verify(mockKafkaSender, times(1)).sendMessage(anyString());
		
	}

}
