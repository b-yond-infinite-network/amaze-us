package com.challenge.suitecrm.api.customers.repository;

import com.challenge.suitecrm.cassandra.table.Recommendation;
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
public class RecommendationsRepository {

    private static final String FIND_BY_CUSTOMER_ID =
        "select * from " + Recommendation.TABLE+ " where customerid=? ALLOW FILTERING";

    private final Session cassandraConnection;
    private final Mapper<Recommendation> mapper;
    private final PreparedStatement preparedStatementFindByIdCustomer;

    @Autowired
    public RecommendationsRepository(Session session) {
        this.cassandraConnection = session;

        this.preparedStatementFindByIdCustomer = session.prepare(FIND_BY_CUSTOMER_ID);
        this.mapper = new MappingManager(session).mapper(Recommendation.class);
    }

    public List<Recommendation> findByCustomerId(String id) {
        List<Recommendation> result = new ArrayList<>();
        ResultSet rs = cassandraConnection.execute(preparedStatementFindByIdCustomer.bind(id));
        result.addAll(mapper.map(rs).all());
        return result;
    }

}
