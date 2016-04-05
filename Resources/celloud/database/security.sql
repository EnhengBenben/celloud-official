# 权限管理数据库表结构及数据初始化

use celloud;

DROP TABLE IF EXISTS `tb_sec_resource`;
CREATE TABLE `tb_sec_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '资源名字，如：用户新增、用户删除等',
  `type` varchar(45) NOT NULL COMMENT '资源类型，如菜单、按钮等',
  `priority` int(11) NOT NULL COMMENT '显示顺序',
  `parent_id` int(11) NOT NULL,
  `permission` varchar(100) NOT NULL COMMENT '权限，如：用户新增：user:create;用户删除:user:delete等',
  `disabled` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已被禁用，0=未禁用；1=已禁用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tb_sec_role`;
CREATE TABLE `tb_sec_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL COMMENT '角色名字',
  `description` varchar(255) DEFAULT NULL COMMENT '角色描述',
  `disabled` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已被禁用，0=未禁用；1=已禁用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tb_sec_user_role_relat`;
CREATE TABLE `tb_sec_user_role_relat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tb_sec_permission`;
CREATE TABLE `tb_sec_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL,
  `resource_id` int(11) NOT NULL,
  PRIMARY KEY (`role_id`,`resource_id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
# 初始化资源，即：实验管理菜单、统计菜单
INSERT INTO `celloud`.`tb_sec_resource` (`id`,`name`, `type`, `priority`, `parent_id`, `permission`) VALUES ('1','实验管理', 'menu', '1', '0', 'experiment:menu');
INSERT INTO `celloud`.`tb_sec_resource` (`id`,`name`, `type`, `priority`, `parent_id`, `permission`) VALUES ('2','统计', 'menu', '2', '0', 'count:menu');
# 初始化角色，即：使用实验和统计两个菜单的角色
INSERT INTO `celloud`.`tb_sec_role` (`id`,`name`, `description`) VALUES ('1','experimenter', '实验管理人员，使用实验管理的人');
INSERT INTO `celloud`.`tb_sec_role` (`id`,`name`, `description`) VALUES ('2','partner', '合作者，即所有的大客户');
# 初始化权限，即：将实验管理的角色和实验管理菜单进行关联、将使用统计菜单的大客户与统计菜单关联
INSERT INTO `celloud`.`tb_sec_permission` (`id`, `role_id`, `resource_id`) VALUES ('1', '1', '1');
INSERT INTO `celloud`.`tb_sec_permission` (`id`, `role_id`, `resource_id`) VALUES ('2', '2', '2');
# 初始化用户角色关系
insert into tb_sec_user_role_relat (`user_id`,`role_id`) select user_id,1 from tb_user_company_relat where company_id=6;

insert into tb_sec_user_role_relat (`user_id`,`role_id`) select user_id,2 from tb_user_company_relat where company_id in (3,6,33);


