package ca.antaki.www.cat.producer.handler;

import java.util.List;

import ca.antaki.www.cat.producer.model.Cat;

public interface CatMoodHandler {

	void handle(List<Cat> cats);
}
