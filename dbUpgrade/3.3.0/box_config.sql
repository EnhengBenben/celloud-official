CREATE TABLE `celloud-box`.`tb_box_config` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `name` VARCHAR(255) NOT NULL COMMENT '盒子的名称标识',
  `user_id` INT(11) NOT NULL COMMENT '使用的用户',
  `intranet_address` VARCHAR(45) NOT NULL COMMENT '内网ip地址',
  `extranet_address` VARCHAR(45) NULL COMMENT '外网ip地址',
  `port` INT(11) NOT NULL COMMENT '端口号',
  `context` VARCHAR(45) NULL COMMENT '应用名称',
  `remark` VARCHAR(550) NULL COMMENT '备注',
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC)  );