CREATE TABLE semester_log (
  id int(11) NOT NULL AUTO_INCREMENT,
  semester int(2) NOT NULL,
  year int(5) NOT NULL,
  started date DEFAULT NULL,
  ended date DEFAULT NULL,
  PRIMARY KEY (id)
);

INSERT INTO semester_log
VALUES (1, 2,2017, "2017-11-06", "2018-03-02");