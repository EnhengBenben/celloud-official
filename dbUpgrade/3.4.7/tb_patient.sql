/*!40101 SET NAMES utf8 */;

DROP TABLE IF EXISTS `tb_patient`;

CREATE TABLE `tb_patient` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `tel` varchar(11) DEFAULT NULL COMMENT '手机',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `id_card` varchar(50) DEFAULT NULL COMMENT '身份证号',
  `gender` int(1) DEFAULT NULL COMMENT '性别 0:女 1:男',
  `weight` varchar(50) DEFAULT NULL COMMENT '体重',
  `height` varchar(50) DEFAULT NULL COMMENT '身高',
  `email` varchar(50) DEFAULT NULL COMMENT '电子邮箱',
  `smoke` int(1) DEFAULT NULL COMMENT '吸烟 0:否 1:是',
  `personal_history` varchar(255) DEFAULT NULL COMMENT '个人史',
  `family_history` varchar(255) DEFAULT NULL COMMENT '家族史',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

