CREATE TABLE cats_mood (
  time  TIMESTAMPTZ NOT NULL,
  name    TEXT    NOT NULL,
  mood SMALLINT NOT  NULL
);

SELECT create_hypertable('cats_mood', 'time');