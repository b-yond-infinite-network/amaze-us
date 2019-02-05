package com.challenge.suitecrm.api.customers.controller;

import com.challenge.suitecrm.api.customers.mapper.Mapper;
import com.challenge.suitecrm.api.customers.model.Customer;
import com.challenge.suitecrm.api.customers.model.Recommendation;
import com.challenge.suitecrm.api.customers.repository.CustomersRepository;
import com.challenge.suitecrm.api.customers.repository.RecommendationsRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

@Service
class DefaultApiDelegate implements CustomersApiDelegate {

    private final CustomersRepository customersRepository;
    private final RecommendationsRepository recommendationsRepository;


    @Autowired
    public DefaultApiDelegate(CustomersRepository customersRepository, RecommendationsRepository recommendationsRepository) {
        this.customersRepository = customersRepository;
        this.recommendationsRepository = recommendationsRepository;
    }

    @Override
    public ResponseEntity<List<Customer>> getAll() {
        List<com.challenge.suitecrm.cassandra.table.Customer> customers = customersRepository.getAll();
        List<Customer>  result=null;
        if (customers!=null)  result = customers.stream()
                .map(Mapper.INSTANCE::as)
            .collect(Collectors.toList());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Customer> findById(String id) {
        com.challenge.suitecrm.cassandra.table.Customer customer = customersRepository.findCustomerById(id).orElseThrow(() -> new ResourceNotFoundException(
            ResourceType.Customer, id));
        Customer result = Mapper.INSTANCE.as(customer);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Recommendation>> findRecommendation( String  id) {
        List<com.challenge.suitecrm.cassandra.table.Recommendation> recommendations = recommendationsRepository.findByCustomerId(id);
        List<Recommendation>  recommendationList = new ArrayList<>();
        if (recommendations!=null)  recommendationList = recommendations.stream()
            .map(Mapper.INSTANCE::as)
            .collect(Collectors.toList());
        return new ResponseEntity<>(recommendationList, HttpStatus.OK);
    }


    private enum ResourceType {
        Customer
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND) private class ResourceNotFoundException
    extends RuntimeException {
        ResourceNotFoundException(ResourceType resource, String id) {
            super(String.format("No such %1$s '%2$s'", resource.name(), id));
        }
    }

}
