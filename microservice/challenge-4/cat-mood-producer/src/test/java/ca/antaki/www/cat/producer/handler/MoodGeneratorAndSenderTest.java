package ca.antaki.www.cat.producer.handler;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ca.antaki.www.cat.producer.model.Cat;

public class MoodGeneratorAndSenderTest {
	

	@Test
	public void test() {
		Cat cat = new Cat("a");
		cat.setMood((byte)1);
		String result = MoodGeneratorAndSender.convertToJson(cat, 1000l);
		assertEquals("{\"name\":\"a\",\"mood\":1,\"time\":1000}", result);
	}

}
