/*!40101 SET NAMES utf8 */;
ALTER TABLE `tb_sample_storage`
	ADD COLUMN `in_machine` TINYINT(1) NULL DEFAULT '0' AFTER `state`;