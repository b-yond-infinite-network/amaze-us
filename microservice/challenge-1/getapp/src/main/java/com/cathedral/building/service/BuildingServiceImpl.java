package com.cathedral.building.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.SQLGrammarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.cathedral.building.model.CathedralBuilding;
import com.cathedral.building.model.Form;
import com.cathedral.building.repository.BuildingRepository;

@Component
public class BuildingServiceImpl implements BuildingService {

	private static final Logger logger = LoggerFactory.getLogger(BuildingServiceImpl.class);

	@Autowired
	private BuildingRepository buildingRepository;

	public List<Form> getFormData() throws Exception {

		long startTime = System.currentTimeMillis();
		List<CathedralBuilding> cathedralBuilding = new ArrayList<CathedralBuilding>();
		List<Form> formList = null;

		try{
			cathedralBuilding = (List<CathedralBuilding>) buildingRepository.findAll();
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
		
		if(null != cathedralBuilding && !cathedralBuilding.isEmpty()){
			formList = new ArrayList<Form>();
			Form form = null;
			for(CathedralBuilding c : cathedralBuilding){
				form = new Form();
				if(null != c.getName()){
					form.setName(c.getName());
				}
				if(null != c.getEmail()){
					form.setEmail(c.getEmail());
				}
				if(null != c.getDescription()){
					form.setDescription(c.getDescription());
				}
				formList.add(form);
			}
		} else {
			// throw exception NO DATA FOUND
		}
		
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		logger.info("Total time taken to store in database: "+ totalTime);
		
		return formList;	
	}
}
