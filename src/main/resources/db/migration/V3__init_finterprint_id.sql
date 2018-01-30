CREATE TABLE fingerprint (
  id int(11) NOT NULL AUTO_INCREMENT,
  fingerprint_id varchar(50) NOT NULL,
  room varchar(255) NOT NULL,
  PRIMARY KEY (id)
);

INSERT INTO `fingerprint` (`id`, `fingerprint_id`, `room`) VALUES ('1', 'pi-server', 'R904');