CREATE TABLE work_log (
  id int(11) NOT NULL AUTO_INCREMENT,
  user_id int(11) NOT NULL,
  status varchar(50) NOT NULL,
  finger varchar(2050) DEFAULT NULL,
  work_date date DEFAULT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES user(id)
);