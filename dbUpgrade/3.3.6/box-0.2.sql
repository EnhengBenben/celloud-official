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

ALTER TABLE `celloud`.`tb_file` 
CHANGE COLUMN `upload_state` `upload_state` INT(11) NOT NULL DEFAULT '0' COMMENT '文件上传状态:0=已上传到盒子，1=已上传到oss，2=上传oss失败，3=已下载到平台，4下载到平台失败' ;

CREATE TABLE `celloud`.`tb_oss_config` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `name` VARCHAR(45) NOT NULL COMMENT '配置的名字，用来区分不同配置',
  `key_id` VARCHAR(255) NOT NULL COMMENT 'oss访问密钥的id',
  `key_secret` VARCHAR(255) NOT NULL COMMENT 'oss访问密钥的密码',
  `endpoint` VARCHAR(255) NOT NULL COMMENT 'oss存储的端点',
  `bucket` VARCHAR(45) NOT NULL COMMENT 'oss的bucket',
  `create_time` DATETIME NOT NULL COMMENT '次密钥的创建时间',
  `expiration_time` DATETIME NULL COMMENT '次密钥的过期时间',
  `state` INT NOT NULL DEFAULT 0 COMMENT '次密钥的可用状态：0=可用；1=禁用',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) );
