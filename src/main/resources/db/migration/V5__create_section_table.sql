CREATE TABLE section (
  id int(11) NOT NULL AUTO_INCREMENT,
  subject_id int(11) NOT NULL,
  user_id int(11) NOT NULL,
  room varchar(255) NOT NULL,
  semester int(11) DEFAULT NULL,
  year int(11) DEFAULT NULL,
  started date NOT NULL,
  ended date DEFAULT NULL,
  PRIMARY KEY (id)
)