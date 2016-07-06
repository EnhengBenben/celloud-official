CREATE TABLE `tb_user_wechat` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `open_id` varchar(28) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '微信OpenId',
  `union_id` varchar(29) COLLATE utf8_bin DEFAULT NULL COMMENT '微信UnionId',
  `create_date` datetime NOT NULL COMMENT '关联时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

