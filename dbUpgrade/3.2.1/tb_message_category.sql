# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 192.168.22.129 (MySQL 5.5.46-MariaDB)
# Database: celloud
# Generation Time: 2016-07-14 01:21:35 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table tb_message_category
# ------------------------------------------------------------

DROP TABLE IF EXISTS `tb_message_category`;

CREATE TABLE `tb_message_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '判断标志',
  `name` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '消息提醒分类名称',
  `email` int(1) DEFAULT NULL COMMENT '邮件开关 0-关 1-开',
  `window` int(1) DEFAULT NULL COMMENT '桌面提醒开关 0-关 1-开',
  `wechat` int(1) NOT NULL COMMENT '微信开关 0-关 1-开',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='消息提醒分类表';

LOCK TABLES `tb_message_category` WRITE;
/*!40000 ALTER TABLE `tb_message_category` DISABLE KEYS */;

INSERT INTO `tb_message_category` (`id`, `code`, `name`, `email`, `window`, `wechat`)
VALUES
	(1,X'7368617265',X'E8A2ABE58886E4BAABE68AA5E5918A',0,1,1),
	(2,X'7265706F7274',X'E68AA5E5918AE7949FE68890',0,1,1),
	(3,X'62616C616E636573',X'E8B4A6E688B7E4BD99E9A29DE58F98E69BB4',1,1,1),
	(4,X'6E6F74696365',X'E7B3BBE7BB9FE585ACE5918A',0,1,1),
	(5,X'6C6F67696E',X'E799BBE5BD95',2,2,1);

/*!40000 ALTER TABLE `tb_message_category` ENABLE KEYS */;
UNLOCK TABLES;

truncate tb_user_message_category_relat;

/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
