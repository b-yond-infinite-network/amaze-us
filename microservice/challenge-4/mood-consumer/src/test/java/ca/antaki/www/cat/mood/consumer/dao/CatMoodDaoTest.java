package ca.antaki.www.cat.mood.consumer.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import ca.antaki.www.cat.mood.consumer.listener.CatMood;

@RunWith(SpringRunner.class)
@JdbcTest
@ComponentScan
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class CatMoodDaoTest {

	@Autowired
	private CatMoodDao catMoodDao;

	@Test
	public void testInsertCatMoods() {
		
		CatMood catMood = new CatMood("cat1",(byte) 1, 1l);
		
		CatMood catMood2 = new CatMood("cat1",(byte) 2, 2l);
		
		List<CatMood> catMoods = List.of(catMood, catMood2);
		int[] updateCount = catMoodDao.insertCatMoods(catMoods);
		assertEquals(2, updateCount.length);
	}

}
