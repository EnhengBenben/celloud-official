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
ALTER TABLE `tb_sample`
	ADD COLUMN `type` VARCHAR(50) NULL DEFAULT NULL COMMENT '样本类型' AFTER `is_add`,
	ADD COLUMN `index` VARCHAR(50) NULL DEFAULT NULL COMMENT '样本index' AFTER `type`;

-- 导出  表 celloud.tb_sample_log 结构
CREATE TABLE IF NOT EXISTS `tb_sample_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sample_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL COMMENT '操作人',
  `exper_state` int(11) NOT NULL DEFAULT '0' COMMENT '实验状态 0-采样  1-入库   2-提取DNA 3-建库',
  `create_date` datetime NOT NULL COMMENT '状态修改时间',
  `state` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否失效 0-未失效  1-已失效',
  PRIMARY KEY (`id`),
  KEY `sample_id` (`sample_id`),
  KEY `user_id` (`user_id`),
  KEY `state` (`state`),
  KEY `exper_state` (`exper_state`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='样本状态修改记录';

-- 数据导出被取消选择。


-- 导出  表 celloud.tb_sample_storage 结构
CREATE TABLE IF NOT EXISTS `tb_sample_storage` (
  `id` int(11) NOT NULL,
  `storage_name` varchar(50) DEFAULT NULL COMMENT '样本库名',
  `index` varchar(50) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `sample_num` int(2) NOT NULL COMMENT '样本数量'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='样本库';

-- 导出  表 celloud.tb_sample_storage_relat 结构
CREATE TABLE IF NOT EXISTS `tb_sample_storage_relat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sample_id` int(11) NOT NULL COMMENT '样本编号',
  `storage_id` int(11) NOT NULL COMMENT '样本库编号',
  PRIMARY KEY (`id`),
  KEY `sample_id` (`sample_id`),
  KEY `storage_id` (`storage_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='样本与库关系表';

