/*!40101 SET NAMES utf8 */;
/*
Navicat MySQL Data Transfer

Source Server         : 本地mysql
Source Server Version : 50548
Source Host           : localhost:3306
Source Database       : celloud

Target Server Type    : MYSQL
Target Server Version : 50548
File Encoding         : 65001

Date: 2016-12-02 11:51:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_company_email
-- ----------------------------
DROP TABLE IF EXISTS `tb_company_email`;
CREATE TABLE `tb_company_email` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `email` varchar(100) NOT NULL COMMENT '邮箱',
  `name` varchar(100) DEFAULT '' COMMENT '姓名',
  `company_id` int(11) DEFAULT NULL COMMENT '公司id',
  `status` int(2) DEFAULT NULL COMMENT '是否是测试人员:0-是,1-不是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
