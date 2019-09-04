package com.cathedral.building.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cathedral.building.model.CathedralBuilding;

@Repository
public interface BuildingRepository extends CrudRepository<CathedralBuilding, Long> {
	
	

}
