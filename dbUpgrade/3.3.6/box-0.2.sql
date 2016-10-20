/*!40101 SET NAMES utf8 */;

ALTER TABLE `tb_box_config` 
DROP COLUMN `user_id`,
ADD COLUMN `serial_number` VARCHAR(45) NOT NULL COMMENT '盒子序列号' AFTER `name`,
ADD COLUMN `location` VARCHAR(45) NULL COMMENT '盒子位置' AFTER `serial_number`,
ADD COLUMN `last_alive` DATETIME NULL COMMENT '上次活跃时间' AFTER `context`,
ADD COLUMN `alive` INT(2) NULL DEFAULT 0 COMMENT '该盒子当前是否活跃：0=活跃；1=不活跃' AFTER `last_alive`;

CREATE TABLE `tb_box_user_relate` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `user_id` INT NOT NULL COMMENT '用户id',
  `box_id` INT NOT NULL COMMENT '盒子id',
  PRIMARY KEY (`id`)
  );

ALTER TABLE `tb_box_config` 
ADD COLUMN `version` VARCHAR(45) NULL COMMENT '盒子内置应用版本号' AFTER `context`;
