-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.5.5-10.0.13-MariaDB - mariadb.org binary distribution
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  8.3.0.4694
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出 celloud 的数据库结构
CREATE DATABASE IF NOT EXISTS `celloud` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `celloud`;


-- 导出  表 celloud.tb_sec_permission 结构
DROP TABLE IF EXISTS `tb_sec_permission`;
CREATE TABLE IF NOT EXISTS `tb_sec_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL,
  `resource_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- 正在导出表  celloud.tb_sec_permission 的数据：~9 rows (大约)
/*!40000 ALTER TABLE `tb_sec_permission` DISABLE KEYS */;
INSERT INTO `tb_sec_permission` (`id`, `role_id`, `resource_id`) VALUES
	(1, 1, 1),
	(2, 1, 2),
	(3, 1, 3),
	(4, 1, 4),
	(5, 1, 5),
	(6, 2, 6),
	(7, 3, 7),
	(8, 4, 8),
	(9, 5, 9);
/*!40000 ALTER TABLE `tb_sec_permission` ENABLE KEYS */;


-- 导出  表 celloud.tb_sec_resource 结构
DROP TABLE IF EXISTS `tb_sec_resource`;
CREATE TABLE IF NOT EXISTS `tb_sec_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '资源名字，如：用户新增、用户删除等',
  `type` varchar(45) NOT NULL COMMENT '资源类型，如菜单、按钮等',
  `priority` int(11) NOT NULL COMMENT '显示顺序',
  `parent_id` int(11) NOT NULL,
  `permission` varchar(100) NOT NULL COMMENT '权限，如：用户新增：user:create;用户删除:user:delete等',
  `disabled` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已被禁用，0=未禁用；1=已禁用',
  `create_date` datetime DEFAULT NULL COMMENT '����ʱ��',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- 正在导出表  celloud.tb_sec_resource 的数据：~9 rows (大约)
/*!40000 ALTER TABLE `tb_sec_resource` DISABLE KEYS */;
INSERT INTO `tb_sec_resource` (`id`, `name`, `type`, `priority`, `parent_id`, `permission`, `disabled`, `create_date`) VALUES
	(1, '我的控制台', 'menu', 0, 0, 'overview:menu', 0, '2016-09-23 15:28:18'),
	(2, '数据上传', 'menu', 0, 0, 'upload:menu', 0, '2016-09-23 15:28:18'),
	(3, '数据管理', 'product', 0, 0, 'data:menu', 0, '2016-09-23 15:28:18'),
	(4, '运行计算', 'product', 0, 0, 'run:button', 0, '2016-09-23 15:28:18'),
	(5, '统计', 'button', 0, 0, 'count:menu', 0, '2016-09-23 15:28:18'),
	(6, '采样', 'button', 0, 0, 'sampling:menu', 0, '2016-09-23 15:28:18'),
	(7, '实验管理', 'button', 0, 0, 'experiment:menu', 0, '2016-09-23 15:28:18'),
	(8, '数据报告', 'button', 0, 0, 'dataReport:menu', 0, '2016-09-23 15:28:18'),
	(9, '项目报告', 'button', 0, 0, 'proReport:menu', 0, '2016-09-23 15:28:18');
/*!40000 ALTER TABLE `tb_sec_resource` ENABLE KEYS */;


-- 导出  表 celloud.tb_sec_role 结构
DROP TABLE IF EXISTS `tb_sec_role`;
CREATE TABLE IF NOT EXISTS `tb_sec_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(45) NOT NULL,
  `description` varchar(255) DEFAULT NULL COMMENT '角色描述',
  `disabled` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已被禁用，0=未禁用；1=已禁用',
  `name` varchar(45) NOT NULL COMMENT '名称',
  `create_date` datetime DEFAULT NULL COMMENT '����ʱ��',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idnew_table_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- 正在导出表  celloud.tb_sec_role 的数据：~5 rows (大约)
/*!40000 ALTER TABLE `tb_sec_role` DISABLE KEYS */;
INSERT INTO `tb_sec_role` (`id`, `code`, `description`, `disabled`, `name`, `create_date`) VALUES
	(1, 'data', '基本数据角色', 0, '基本', '2016-09-23 00:00:00'),
	(2, 'sampling', '采样人员', 0, '采样员', '2016-09-23 00:00:00'),
	(3, 'experiment', '实验员角色', 0, '实验员', '2016-09-23 00:00:00'),
	(4, 'dataReporter', '可查看数据报告的人员', 0, '数据报告人员', '2016-09-23 00:00:00'),
	(5, 'projectReporter', '可查看项目报告的人员', 0, '项目报告人员', '2016-09-23 00:00:00');

DROP TABLE IF EXISTS `tb_sec_user_role_relat`;
CREATE TABLE IF NOT EXISTS `tb_sec_user_role_relat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
insert into tb_sec_user_role_relat (user_id,role_id) select user_id,1 from tb_user where role = 0 order by user_id;	
insert into tb_sec_user_role_relat (user_id,role_id) values(100,2),(100,3),(16,2),(16,3);
insert into tb_sec_user_role_relat (user_id,role_id) 
select u.user_id,4 from tb_user u,tb_user_company_relat uc 
where u.user_id=uc.user_id and uc.company_id in (45,1);

insert into tb_sec_user_role_relat (user_id,role_id) 
select u.user_id,5 from tb_user u,tb_user_company_relat uc 
where u.user_id=uc.user_id and uc.company_id not in (45,1);
/*!40000 ALTER TABLE `tb_sec_role` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
