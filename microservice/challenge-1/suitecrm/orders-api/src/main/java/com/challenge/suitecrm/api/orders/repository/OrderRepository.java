package com.challenge.suitecrm.api.orders.repository;

import com.challenge.suitecrm.cassandra.table.Order;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {

    private static final String FIND_BY_CUSTOMER_ID =
        "select * from " + Order.TABLE+ " where customerid=? ALLOW FILTERING";

    private static final String SELECT_ALL_ =
        "select * from " + Order.TABLE;

    private final Session cassandraConnection;
    private final Mapper<Order> mapper;

    private final PreparedStatement preparedStatementFindByIdCustomer;

    @Autowired
    public OrderRepository(Session session) {
        this.cassandraConnection = session;

        this.preparedStatementFindByIdCustomer = session.prepare(FIND_BY_CUSTOMER_ID);
        this.mapper = new MappingManager(session).mapper(Order.class);
    }

    public List<Order> getAll() {
        List<Order> result = new ArrayList<>();
        ResultSet rs = cassandraConnection.execute(SELECT_ALL_);
        result.addAll(mapper.map(rs).all());
        return result;
    }

    public List<Order> findByCustomerId(String id) {
        List<Order> result = new ArrayList<>();
        ResultSet rs = cassandraConnection.execute(preparedStatementFindByIdCustomer.bind(id));
        result.addAll(mapper.map(rs).all());
        return result;
    }

}
