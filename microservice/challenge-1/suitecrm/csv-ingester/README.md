Csv ingester
=====================
This module simulate a Change Data Capture or an ETL to ingest data to Apache Kafka.

It watch events on a directory datasets an send new added csv data file to Kafka

Example of SuiteCrm datasets dir tree pattern:
```
/datasets/CUSTOMERS/ingest_**.csv
/datasets/ORDERS/ingest_**.csv
/datasets/PRODUCTS/ingest_**.csv
/datasets/PRODUCTS_RELATIONS/ingest_**.csv
```

### Usage :

```
java -jar csv-ingester.jar -d < dataset path >
``` 
