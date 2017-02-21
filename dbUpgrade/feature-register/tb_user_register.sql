/*!40101 SET NAMES utf8 */;
ALTER TABLE `tb_user_register`
	ADD COLUMN `truename` VARCHAR(45) NULL DEFAULT NULL COMMENT '真实姓名' AFTER `email`;