CREATE KEYSPACE IF NOT EXISTS catz WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

USE catz;

CREATE TABLE IF NOT EXISTS catz.cat_moods(
    cat_id bigint,
    ts timestamp,
    mood bigint,
    PRIMARY KEY(cat_id, ts)
    );
