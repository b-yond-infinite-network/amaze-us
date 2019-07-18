package com.kepler.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kepler.model.Resource;
import com.kepler.model.ResourceVariation;

@Repository
public interface ResourceVariationRepository extends JpaRepository<ResourceVariation, Long> {
	
	/* ********************************************************************************************************* *
	 * CRUD requests are missing but are implemented due to the extension with JpaRepostroy<ENtity, primary_key> * 
	 * CRUD : Create / Read / Update and Delete requests                                                         * 
	 * ********************************************************************************************************* *
	 * If a function requires to apply a complex request, the association @Query(the_request) can be used before */
	
	/* Next queries are added due to custom needs */
	Page<ResourceVariation> findByResourceVariationId(Long resourceVariationId, Pageable pageable);
	
	Page<ResourceVariation> findByResourceVariationValue(float resourceVariationValue, Pageable pageable);
	Page<ResourceVariation> findByResourceVariationDate(Date resourceVariationDate, Pageable pageable);
	Page<ResourceVariation> findByResourceVariationApplication(boolean resourceVariationApplication, Pageable pageable);

	Page<ResourceVariation> findByResource(Resource resource, Pageable pageable);
}
