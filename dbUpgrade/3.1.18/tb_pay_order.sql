CREATE TABLE `tb_pay_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `type` tinyint(2) NOT NULL COMMENT '订单类型，支付宝、网银等',
  `trade_no` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '交易编号',
  `subject` varchar(45) COLLATE utf8_bin NOT NULL COMMENT '商品名称',
  `description` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '商品描述',
  `amount` decimal(10,2) NOT NULL COMMENT '商品金额',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `state` tinyint(1) NOT NULL DEFAULT '0' COMMENT '订单状态 0=未支付；1=已支付',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
