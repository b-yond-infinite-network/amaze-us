package com.kepler.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kepler.model.Resource;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
	
	/* ********************************************************************************************************* *
	 * CRUD requests are missing but are implemented due to the extension with JpaRepostroy<ENtity, primary_key> * 
	 * CRUD : Create / Read / Update and Delete requests                                                         * 
	 * ********************************************************************************************************* *
	 * If a function requires to apply a complex request, the association @Query(the_request) can be used before */
	
	/* Next queries are added due to custom needs */
	Page<Resource> findByResourceId(Long resourceId, Pageable pageable);
	Page<Resource> findByResourceName(String resourceNameName, Pageable pageable);
	
	Page<Resource> findByResourceCriticality(Long resourceCriticality, Pageable pageable);
	Page<Resource> findByResourceQuantityValue(float resourceQuantityValue, Pageable pageable);
	Page<Resource> findByResourceQuantityDate(Date resourceQuantityDate, Pageable pageable);

	Page<Resource> findByResourceWarningLevel(float resourceWarningLevel, Pageable pageable);
	Page<Resource> findByResourceEmergencyLevel(float resourceEmergencyLevel, Pageable pageable);
}
