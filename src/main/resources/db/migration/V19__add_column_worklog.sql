ALTER TABLE `work_log` ADD `clock_in_sec` INT(7) NOT NULL AFTER `work_date`;
ALTER TABLE `work_log` ADD `clock_in_time` TIME NOT NULL DEFAULT '0' AFTER `work_date`;