-- MySQL dump 10.13  Distrib 5.6.24, for osx10.8 (x86_64)
--
-- Host: 192.168.22.253    Database: celloud
-- ------------------------------------------------------
-- Server version	5.1.73

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tb_oss_config`
--

DROP TABLE IF EXISTS `tb_oss_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_oss_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `name` varchar(45) COLLATE utf8_bin NOT NULL COMMENT '配置的名字，用来区分不同配置',
  `key_id` varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'oss访问密钥的id',
  `key_secret` varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'oss访问密钥的密码',
  `endpoint` varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'oss存储的端点',
  `bucket` varchar(45) COLLATE utf8_bin NOT NULL COMMENT 'oss的bucket',
  `create_time` datetime NOT NULL COMMENT '次密钥的创建时间',
  `expiration_time` datetime DEFAULT NULL COMMENT '次密钥的过期时间',
  `state` int(11) NOT NULL DEFAULT '0' COMMENT '次密钥的可用状态：0=可用；1=禁用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_oss_config`
--

LOCK TABLES `tb_oss_config` WRITE;
/*!40000 ALTER TABLE `tb_oss_config` DISABLE KEYS */;
INSERT INTO `tb_oss_config` VALUES (1,'test','UrKY3uHmTuwajVKU','zK4cU4cEHPEnQNxu5NnnmCSvBHXuOd','http://oss-cn-shanghai.aliyuncs.com','celloud','2016-10-20 14:19:15',NULL,0);
/*!40000 ALTER TABLE `tb_oss_config` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-10-20 17:06:33
