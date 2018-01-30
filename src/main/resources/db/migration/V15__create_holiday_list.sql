CREATE TABLE holiday (
  id int(11) NOT NULL AUTO_INCREMENT,
  holiday_name varchar(255) NOT NULL,
  holiday_date date DEFAULT NULL,
  PRIMARY KEY (id)
);

INSERT INTO holiday
VALUES (1, "Makabucha", "2018-03-01");

INSERT INTO holiday
VALUES (2, "Jakkee", "2018-04-06");

INSERT INTO holiday
VALUES (3, "Songkarn", "2018-04-13");

INSERT INTO holiday
VALUES (4, "Songkarn", "2018-04-16");

INSERT INTO holiday
VALUES (5, "PueedchMongkol", "2018-05-14");