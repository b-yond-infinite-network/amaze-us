package ca.antaki.www.cat.mood.consumer.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ca.antaki.www.cat.mood.consumer.listener.CatMood;


@Repository
public class CatMoodDao {
	private JdbcTemplate template;  
	
	@Autowired	
	public CatMoodDao(JdbcTemplate template) {
		this.template = template;
	}

	public int[] insertCatMoods(List<CatMood> catMoods) {
		 final String sql = "insert into cats_mood(time, name, mood) values(?,?,?)";
		 int[] updateCounts =  template.batchUpdate(sql, new BatchPreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					CatMood catMood = catMoods.get(i);
					ps.setTimestamp(1, new Timestamp(catMood.getTime()));
					ps.setString(2, catMood.getName());
					ps.setInt(3, catMood.getMood());
				}
						
				@Override
				public int getBatchSize() {
					return catMoods.size();
				}
			  });
		 
		 return updateCounts;

	}
}
