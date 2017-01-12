/*!40101 SET NAMES utf8 */;
ALTER TABLE `tb_task`
	ADD COLUMN `read` `read` TINYINT(1) NULL DEFAULT '0' COMMENT '是否已读  0-未读  1-已读' AFTER `sample_id`;