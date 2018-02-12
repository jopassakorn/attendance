ALTER TABLE `section_log` ADD `note` varchar(255) AFTER `clock_in_sec`;
ALTER TABLE `section_log` ADD `is_note_wrttten` tinyint(1) NOT NULL DEFAULT FALSE AFTER `note`;
ALTER TABLE `work_log` ADD `note` varchar(255) AFTER `clock_in_sec`;
ALTER TABLE `work_log` ADD `is_note_wrttten` tinyint(1) NOT NULL DEFAULT FALSE AFTER `note`;