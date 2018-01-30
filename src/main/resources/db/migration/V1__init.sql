CREATE TABLE user (
  id int(11) NOT NULL AUTO_INCREMENT,
  first_name varchar(50) NOT NULL,
  last_name varchar(50) DEFAULT NULL,
  email varchar(255) NOT NULL,
  PRIMARY KEY (id)
);