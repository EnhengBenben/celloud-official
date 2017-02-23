/*!40101 SET NAMES utf8 */;

/*增加是否收款字段*/
ALTER TABLE `tb_sample` ADD COLUMN `is_pay` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '是否收款：0-未收款  1-已收款' AFTER `patient_id`;