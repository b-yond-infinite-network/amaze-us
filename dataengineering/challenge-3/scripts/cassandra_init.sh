#!/bin/bash
until /opt/bitnami/cassandra/bin/cqlsh  -u cassandra -p cassandra -e "CREATE KEYSPACE evilnet WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 1};"; do
  sleep 10
done
until /opt/bitnami/cassandra/bin/cqlsh  -u cassandra -p cassandra -e "CREATE TABLE evilnet.tweets( count double,city text ,date timestamp, primary key (city,date) );"; do
  sleep 10
done

until /opt/bitnami/cassandra/bin/cqlsh  -u cassandra -p cassandra -e "CREATE TABLE evilnet.retweets( count double,city text ,date timestamp, primary key (city,date) );"; do
  sleep 10
done

until /opt/bitnami/cassandra/bin/cqlsh  -u cassandra -p cassandra -e "CREATE TABLE evilnet.uniqueusers( count double,city text ,date timestamp, primary key (city,date)  );"; do
  sleep 10
done


sleep infinity