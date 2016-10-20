CREATE TABLE `tb_recharge_alipay` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `buyer_email` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '支付者的邮箱',
  `buyer_id` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '支付者的支付宝id',
  `trade_no` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '系统内的交易编号',
  `ali_trade_no` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '支付宝的交易编号',
  `subject` varchar(45) COLLATE utf8_bin NOT NULL COMMENT '商品名称',
  `description` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '商品描述',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `amount` decimal(10,2) NOT NULL COMMENT '支付金额',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
