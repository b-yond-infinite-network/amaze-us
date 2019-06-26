package ca.antaki.www.cat.producer.retriever;

import java.util.List;

import ca.antaki.www.cat.producer.model.Cat;

public interface CatRetriever {

	public List<Cat> retrieve(int amount);
}
