package com.challenge.suitecrm.cassandra.table;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(name = Customer.TABLE)
public class Customer {
    
    public static final String TABLE = "customers";

    @PartitionKey
    @Column(name = "customerid")
    public String customerId;

    @Column(name = "fullname")
    public String fullname;

    @Column(name = "address")
    public String address;

    @Column(name = "phone")
    public String phone;

    @Column(name = "email")
    public String email;

    @Column(name = "stopemail")
    public Boolean stopEmail;

    @Column(name = "stopphone")
    public Boolean stopPhone;
}
