ALTER TABLE user
DROP COLUMN activated;

CREATE TABLE activated (
  id int(11) NOT NULL AUTO_INCREMENT,
  user_id int(11),
  fingerprint_id int(11),
  activated_status tinyint(1) DEFAULT FALSE,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES user(id),
  FOREIGN KEY (fingerprint_id) REFERENCES fingerprint(id)
);