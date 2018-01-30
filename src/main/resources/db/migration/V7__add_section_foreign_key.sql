ALTER TABLE section
ADD FOREIGN KEY (subject_id) REFERENCES subject(id);
