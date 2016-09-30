/*!40101 SET NAMES utf8 */;

ALTER TABLE `celloud`.`tb_task` 
ADD COLUMN `datalist` VARCHAR(300) NULL COMMENT 'datalist path',
ADD COLUMN `result` VARCHAR(300) NULL COMMENT '结果路径';
