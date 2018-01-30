CREATE TABLE section_log (
  id int(11) NOT NULL AUTO_INCREMENT,
  section_id int(11) NOT NULL,
  status varchar(50) NOT NULL,
  finger varchar(2050) DEFAULT NULL,
  work_date date DEFAULT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (section_id) REFERENCES section(id)
)