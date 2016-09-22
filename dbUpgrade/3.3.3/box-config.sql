use celloud;
CREATE TABLE `tb_box_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '盒子的名称标识',
  `user_id` int(11) NOT NULL COMMENT '使用的用户',
  `intranet_address` varchar(45) COLLATE utf8_bin NOT NULL COMMENT '内网ip地址',
  `extranet_address` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '外网ip地址',
  `port` int(11) NOT NULL COMMENT '端口号',
  `context` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '应用名称',
  `remark` varchar(550) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
