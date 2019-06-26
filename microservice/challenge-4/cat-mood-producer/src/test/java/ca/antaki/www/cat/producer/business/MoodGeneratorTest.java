package ca.antaki.www.cat.producer.business;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class MoodGeneratorTest {

	private MoodGenerator testee;

	@Before
	public void setUp() {
		testee = new MoodGenerator();

	}

	@Test
	public void test() {
		for (int i = 0; i < 100; i++) {
			int res = testee.generateMood();
			assertTrue(res >= 0 && res < 8);
		}
	}

}
