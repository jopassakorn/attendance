ALTER TABLE subject
ADD delete_status BOOLEAN NOT NULL DEFAULT FALSE AFTER `edited`;