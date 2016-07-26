/*!40101 SET NAMES utf8 */;
ALTER TABLE `celloud`.`tb_pay_order` 
ADD COLUMN `bank_code` VARCHAR(45) NULL COMMENT '' AFTER `type`;
