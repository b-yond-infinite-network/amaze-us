CREATE DATABASE booster;
USE booster;

CREATE USER 'developer'@'localhost' IDENTIFIED BY 'password';
GRANT ALL ON *.* TO 'developer'@'localhost';