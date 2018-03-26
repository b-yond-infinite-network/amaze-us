CREATE DATABASE `reminders` /*!40100 DEFAULT CHARACTER SET utf8 */;

CREATE TABLE `reminders` (
  `id` int(11) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `isComplete` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;