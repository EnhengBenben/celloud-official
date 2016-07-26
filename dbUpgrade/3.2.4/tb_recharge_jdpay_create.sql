/*!40101 SET NAMES utf8 */;
DROP TABLE IF EXISTS `tb_message_category`;
CREATE TABLE `tb_recharge_jdpay` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `trade_no` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '系统内的交易编号',
  `bank_code` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '银行的编号',
  `bank_name` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '支付方式中文说明，如"中行长城信用卡"',
  `subject` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '商品名称',
  `description` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '商品描述',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `money_type` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '币种',
  `amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '支付金额',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
