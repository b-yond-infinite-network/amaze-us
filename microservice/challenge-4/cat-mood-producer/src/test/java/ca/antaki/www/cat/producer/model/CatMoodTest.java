package ca.antaki.www.cat.producer.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class CatMoodTest {

	@Test
	public void test() {
		assertTrue(CatMood.getById((byte)0) == CatMood.MIAW);
		assertTrue(CatMood.getById((byte)1) == CatMood.GROWL);
		assertTrue(CatMood.getById((byte)2) == CatMood.HISS);
		assertTrue(CatMood.getById((byte)3) == CatMood.PURR);
		assertTrue(CatMood.getById((byte)4) == CatMood.THROW_GLASS);
		assertTrue(CatMood.getById((byte)5) == CatMood.ROLL_ON_FLOOR);
		assertTrue(CatMood.getById((byte)6) == CatMood.SCRATCH_CHAIRS);
		assertTrue(CatMood.getById((byte)7) == CatMood.LOOK_DEEP_IN_EYES);
	}

}
