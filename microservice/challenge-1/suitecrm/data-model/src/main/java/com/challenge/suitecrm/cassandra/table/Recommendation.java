package com.challenge.suitecrm.cassandra.table;

import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import java.util.Map;

@Table(name = Recommendation.TABLE)
public class Recommendation {

    public static final String TABLE = "recommendation";

    @PartitionKey
    @Column(name = "customerid")
    public String customerId;

    @ClusteringColumn()
    @Column(name = "timestamp")
    public String timestamp;

    @Column(name = "products")
    public Map<String,String> products;


}
