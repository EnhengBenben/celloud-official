-- --------------------------------------------------------
-- 主机:                           192.168.22.129
-- 服务器版本:                        5.5.46-MariaDB - mariadb.org binary distribution
-- 服务器操作系统:                      Win32
-- HeidiSQL 版本:                  8.3.0.4694
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出 celloud 的数据库结构
CREATE DATABASE IF NOT EXISTS `celloud` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;
USE `celloud`;


-- 导出  表 celloud.tb_recharge_transfer 结构
CREATE TABLE IF NOT EXISTS `tb_recharge_transfer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户编号',
  `account_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '开户名',
  `account_no` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '账号',
  `bank` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '开户行',
  `trade_no` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '交易流水号',
  `summary` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '摘要',
  `back_no` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '电子回单号',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `amount` decimal(10,2) NOT NULL COMMENT '转账金额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='银行转账支付';

-- 数据导出被取消选择。
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
