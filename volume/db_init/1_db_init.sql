-- init mySQL database

-- CREATE SCHEMA
CREATE SCHEMA IF NOT EXISTS Schedules;

-- ME
CREATE USER IF NOT EXISTS 'tester'@'%' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON Schedules.* to 'tester'@'%'
