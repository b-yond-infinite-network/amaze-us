REATE DATABASE `todoapp`;
CREATE USER 'guest' IDENTIFIED BY 'Guest0000!';
GRANT USAGE ON `todoapp`.* TO 'guest'@localhost IDENTIFIED BY 'Guest0000!';
GRANT ALL privileges ON `todoapp`.* TO 'guest'@localhost;
ALTER DATABASE `todoapp` CHARACTER SET utf8 COLLATE utf8_general_ci;
FLUSH PRIVILEGES;

