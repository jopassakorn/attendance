ALTER TABLE section
ADD FOREIGN KEY (user_id) REFERENCES user(id);