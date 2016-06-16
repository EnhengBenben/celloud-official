CREATE TABLE `celloud`.`tb_notice_user` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '表自增主键',
  `notice_id` INT NOT NULL COMMENT 'notice的id',
  `user_id` INT NOT NULL COMMENT '用户id',
  `read_time` TIMESTAMP NULL COMMENT '用户查看notice的时间',
  `state` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '状态标记：0=未读，1=已读',
  PRIMARY KEY (`id`)  COMMENT '',
  UNIQUE INDEX `id_UNIQUE` (`id` ASC)  COMMENT '');


ALTER TABLE `celloud`.`tb_notice` 
ADD COLUMN `category` VARCHAR(45) NULL COMMENT '公告或消息所属的类别' AFTER `notice_context`,
ADD COLUMN `type` VARCHAR(45) NOT NULL COMMENT '类别标识，标识公告或者消息，notice=公告，message=消息' AFTER `state`;
