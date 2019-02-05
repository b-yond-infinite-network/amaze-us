package com.challenge.suitecrm.api.orders.controller;

import com.challenge.suitecrm.api.orders.mapper.Mapper;
import com.challenge.suitecrm.api.orders.model.Order;
import com.challenge.suitecrm.api.orders.repository.OrderRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
class DefaultApiDelegate implements OrdersApiDelegate {

    private final OrderRepository orderRepository;


    @Autowired
    public DefaultApiDelegate(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public ResponseEntity<List<Order>> getAll() {
        List<com.challenge.suitecrm.cassandra.table.Order> orders = orderRepository.getAll();
        List<Order>  result=null;
        if (orders!=null)  result = orders.stream()
                .map(Mapper.INSTANCE::as)
            .collect(Collectors.toList());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
