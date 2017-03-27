/*!40101 SET NAMES utf8 */;

CREATE TABLE `tb_app_comment` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `app_id` int(11) DEFAULT NULL COMMENT '关联app',
  `user_id` int(11) DEFAULT NULL COMMENT '关联user',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `comment` varchar(255) DEFAULT NULL COMMENT '评论',
  `score` int(11) DEFAULT NULL COMMENT '评分(1,2,3,4,5)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


ALTER TABLE tb_app ADD COLUMN classic INT COMMENT '精选:0-是,1-否' AFTER `flag`;
ALTER TABLE tb_app ADD COLUMN version VARCHAR(255) COMMENT '版本信息' AFTER `code`;
ALTER TABLE tb_app ADD COLUMN update_date DATETIME COMMENT '更新时间' AFTER create_date;




