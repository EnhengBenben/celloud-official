/*
Navicat MySQL Data Transfer

Source Server         : 192.168.22.129mysql
Source Server Version : 50546
Source Host           : 192.168.22.129:3306
Source Database       : celloud

Target Server Type    : MYSQL
Target Server Version : 50546
File Encoding         : 65001

Date: 2016-06-30 17:40:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_invoice
-- ----------------------------
DROP TABLE IF EXISTS `tb_invoice`;
CREATE TABLE `tb_invoice` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NOT NULL COMMENT '用户编号',
  `money` decimal(10,2) NOT NULL COMMENT '金额',
  `invoice_type` tinyint(1) NOT NULL COMMENT '类型 0-普票 1-专票',
  `invoice_header` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '抬头',
  `contacts` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '收货人姓名',
  `cellphone` varchar(11) COLLATE utf8_bin NOT NULL COMMENT '收货人手机号码',
  `address` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '邮寄地址',
  `invoice_state` tinyint(1) NOT NULL COMMENT '发票状态 0-已申请：发邮件给我们 1-已寄出：发邮件客户',
  `remark` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '快递公司 快递单号',
  `create_date` datetime NOT NULL COMMENT '申请时间',
  `update_date` datetime DEFAULT NULL COMMENT '寄出时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

