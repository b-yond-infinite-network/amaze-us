package ca.antaki.www.cat.producer.retriever;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import ca.antaki.www.cat.producer.model.Cat;

@Component
public class InMemoryCatRetriever implements CatRetriever {

	@Override
	public List<Cat> retrieve(int amount) {
		List<Cat> list = new ArrayList<>(amount);
		int nameCount = 0;
		for(int i = 0;i < amount;i++) {
			list.add(new Cat("cat"+ (++nameCount)));
		}
		return list;
	}

}
