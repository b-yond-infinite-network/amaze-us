package com.cathedral.building.service;

import java.util.Calendar;

import org.hibernate.exception.SQLGrammarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.cathedral.building.model.CathedralBuilding;
import com.cathedral.building.repository.BuildingRepository;

@Component
public class BuildingServiceImpl implements BuildingService {

	private static final Logger logger = LoggerFactory.getLogger(BuildingServiceImpl.class);

	@Autowired
	private BuildingRepository buildingRepository;

	public void saveForm(String name, String email, String description) throws Exception {

		long startTime = System.currentTimeMillis();

		CathedralBuilding building = new CathedralBuilding();
		Calendar calendar = Calendar.getInstance();
		java.sql.Timestamp currentTms = new java.sql.Timestamp(calendar.getTime().getTime());

		building.setName(name);
		building.setEmail(email);
		building.setDescription(description);
		building.setCreatedtms(currentTms);

		try{

			buildingRepository.save(building);

		} catch(DataAccessException de){ 
			de.printStackTrace();
			throw de;
		} catch(SQLGrammarException se){ 
			se.printStackTrace();
			throw se;
		} catch(Exception e){
			e.printStackTrace();
			throw e;
		}

		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		logger.info("Total time taken to store in database: "+ totalTime);
	}

}
