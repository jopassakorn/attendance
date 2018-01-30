ALTER TABLE section CHANGE `room` `room_id` INT(11) NOT NULL;

ALTER TABLE section
ADD FOREIGN KEY (room_id) REFERENCES fingerprint(id);