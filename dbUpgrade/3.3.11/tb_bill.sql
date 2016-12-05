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

Date: 2016-12-02 11:51:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_bill
-- ----------------------------
DROP TABLE IF EXISTS `tb_bill`;
CREATE TABLE `tb_bill` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `month` varchar(50) DEFAULT NULL COMMENT '月份',
  `quantity` int(11) DEFAULT NULL COMMENT '上传总量',
  `original_price` int(11) DEFAULT NULL COMMENT '原始单价(分)',
  `discount_price` int(11) DEFAULT NULL COMMENT '折后单价(分)',
  `total_price` int(11) DEFAULT NULL COMMENT '总价(分)',
  `state` int(11) DEFAULT NULL COMMENT '是否到账:0-未到账,1-到账',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `company_id` int(11) DEFAULT NULL COMMENT '公司id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
