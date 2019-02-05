package com.challenge.suitecrm.cassandra.table;

import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(name = Order.TABLE)
public class Order {
    
    public static final String TABLE = "orders";

    @PartitionKey
    @Column(name = "customerid")
    public String customerId;

    @ClusteringColumn()
    @Column(name = "orderid")
    public String orderId;

    @Column(name = "amount")
    public Double amount;

    @Column(name = "productcategory")
    public String productCategory;

    @Column(name = "productdescription")
    public String productDescription;

    @Column(name = "productid")
    public String productId;
}
