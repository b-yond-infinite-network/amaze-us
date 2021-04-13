CREATE TABLE countries_stats (
  id INT NOT NULL AUTO_INCREMENT,
  year DATE NOT NULL,
  country_id INT NOT NULL,
  gdp FLOAT NULL,
  population INT NULL,
  nobel_prize_winners INT NULL,
  PRIMARY KEY (id)
);