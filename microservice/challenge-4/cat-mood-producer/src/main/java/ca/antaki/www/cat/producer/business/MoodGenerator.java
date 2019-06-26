package ca.antaki.www.cat.producer.business;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Component;

import ca.antaki.www.cat.producer.model.CatMood;

@Component
public class MoodGenerator {

	public byte generateMood() {
		return (byte) ThreadLocalRandom.current().nextInt(CatMood.MIN, CatMood.MAX);
	}
}
