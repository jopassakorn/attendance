CREATE TABLE subject (
  id int(11) NOT NULL AUTO_INCREMENT,
  code varchar(7) NOT NULL,
  name varchar(255) NOT NULL,
  department varchar(255) DEFAULT NULL,
  faculty varchar(255) DEFAULT NULL,
  created date NOT NULL,
  edited date DEFAULT NULL,
  PRIMARY KEY (id)
);