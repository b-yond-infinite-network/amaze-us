DROP KEYSPACE cats;

CREATE KEYSPACE cats
  WITH REPLICATION = {
   'class' : 'SimpleStrategy',
   'replication_factor' : 1
  };

CREATE TABLE cats.moods (
   name text,
   mood text,
   year int,
   month int,
   day int,
   hour int,
   minute int,
   second int,
   PRIMARY KEY ((name,mood), year, month, day, hour, minute, second));

