CREATE TABLE summer_games (
  id INT NOT NULL AUTO_INCREMENT,
  sport VARCHAR(100) NOT NULL,
  event VARCHAR(300) NOT NULL,
  year DATE NULL,
  athlete_id INT NULL,
  country_id INT NULL,
  medal VARCHAR(100) NULL,
  PRIMARY KEY (id)
);