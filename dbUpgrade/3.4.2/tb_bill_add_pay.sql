ALTER TABLE `tb_bill`
	ADD COLUMN `send` INT(1) NULL DEFAULT '0' COMMENT '是否发送:0-未发送,1-已发送' AFTER `user_id`;
