# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 192.168.22.129 (MySQL 5.5.46-MariaDB)
# Database: celloud
# Generation Time: 2016-07-14 08:12:44 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table tb_user_message_category_relat
# ------------------------------------------------------------

DROP TABLE IF EXISTS `tb_user_message_category_relat`;

CREATE TABLE `tb_user_message_category_relat` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `email` int(1) DEFAULT '0' COMMENT '邮件开关 0-关 1-开',
  `window` int(1) DEFAULT '0' COMMENT '桌面提醒开关 0-关 1-开',
  `wechat` int(1) DEFAULT '0' COMMENT '微信开关 0-关 1开',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `message_category_id` int(11) DEFAULT NULL COMMENT '消息提醒分类id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户消息分类关系表';




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
