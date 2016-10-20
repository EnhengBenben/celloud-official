/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
CREATE DATABASE IF NOT EXISTS `celloud` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `celloud`;

CREATE TABLE `tb_sample_order` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`user_id` INT(11) NOT NULL DEFAULT '0',
	`order_no` VARCHAR(50) NOT NULL COMMENT '�������' COLLATE 'utf8_bin',
	`create_date` DATETIME NOT NULL COMMENT '�µ�����',
	`is_print` INT(11) NOT NULL DEFAULT '0' COMMENT '0:δ��ӡ  1-�Ѵ�ӡ',
	PRIMARY KEY (`id`),
	INDEX `user_id` (`user_id`)
)
COLLATE='utf8_bin'
ENGINE=MyISAM;


ALTER TABLE `tb_sample`
	ALTER `sample_name` DROP DEFAULT;
ALTER TABLE `tb_sample`
	CHANGE COLUMN `sample_name` `sample_name` VARCHAR(50) NOT NULL COMMENT '�����������' AFTER `user_id`,
	ADD COLUMN `exper_sample_name` VARCHAR(50) NULL DEFAULT NULL COMMENT 'ʵ���������' AFTER `sample_name`;
ALTER TABLE `tb_sample`
	ADD COLUMN `order_id` INT(11) NULL DEFAULT '0' COMMENT '��������id' AFTER `user_id`;
