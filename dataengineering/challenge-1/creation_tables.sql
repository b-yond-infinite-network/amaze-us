CREATE OR REPLACE TABLE tmp.winter_games
(
  sport STRING,
  event STRING,
  year DATE,
  athlete_id INT64,
  country_id INT64,
  medal STRING
)
OPTIONS (
  expiration_timestamp=TIMESTAMP ""2021-03-21T18:14:06.100Z""
)
;

CREATE OR REPLACE TABLE tmp.summer_games
(
  sport STRING,
  event STRING,
  year DATE,
  athlete_id INT64,
  country_id INT64,
  medal STRING
)
OPTIONS (
  expiration_timestamp=TIMESTAMP ""2021-03-21T18:13:39.903Z""
)
;

CREATE OR REPLACE TABLE tmp.countries
(
  id INT64,
  country STRING,
  region STRING
)
OPTIONS (
  expiration_timestamp=TIMESTAMP ""2021-03-21T18:12:22.133Z""
)
;

CREATE OR REPLACE TABLE tmp.country_stats
(
  year DATE,
  country_id INT64,
  gdp FLOAT64,
  population INT64,
  nobel_prize_winners INT64
)
OPTIONS (
  expiration_timestamp=TIMESTAMP ""2021-03-21T18:13:04.770Z""
)
;

CREATE OR REPLACE TABLE tmp.athletes
(
  id INT64,
  name STRING,
  gender STRING,
  age INT64,
  height INT64,
  weight INT64
)
OPTIONS (
  expiration_timestamp=TIMESTAMP ""2021-03-21T18:11:08.313Z""
)
;
