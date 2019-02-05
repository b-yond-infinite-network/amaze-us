package com.challenge.suitecrm.api.customers.repository;

import static java.util.Optional.ofNullable;

import com.challenge.suitecrm.cassandra.table.Customer;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CustomersRepository {

    private static final String SELECT_FROM_CUSTOMERS_WHERE_ID =
        "select * from " + Customer.TABLE+ " where customerid=?";

    private static final String SELECT_ALL_CUSTOMERS =
        "select * from " + Customer.TABLE;

    private final Session cassandraConnection;
    private final Mapper<Customer> mapperCustomer;
    private final PreparedStatement preparedStatementFindById;

    @Autowired
    public CustomersRepository(Session session) {
        this.cassandraConnection = session;

        this.preparedStatementFindById = session.prepare(SELECT_FROM_CUSTOMERS_WHERE_ID);
        this.mapperCustomer = new MappingManager(session).mapper(Customer.class);
    }

    public List<Customer> getAll() {
        List<Customer> result = new ArrayList<>();
        ResultSet rs = cassandraConnection.execute(SELECT_ALL_CUSTOMERS);
        result.addAll(mapperCustomer.map(rs).all());
        return result;
    }

    public Optional<Customer> findCustomerById(String id) {
        ResultSet rs = cassandraConnection.execute(preparedStatementFindById.bind(id));
        return ofNullable(mapperCustomer.map(rs).one());
    }

}
