/*--添加用户余额--*/
ALTER TABLE `tb_user`
	ADD COLUMN `balances` DECIMAL(10,2) NULL DEFAULT '0.00' COMMENT '账户余额' AFTER `sign`;

/*--添加充值表--*/
CREATE TABLE `tb_recharge` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`user_id` INT(11) NOT NULL COMMENT '用户编号',
	`recharge_type` TINYINT(1) NOT NULL COMMENT '充值详情表类型：0-支付宝 1-银行转账',
	`recharge_id` INT(11) NOT NULL COMMENT '充值详情表id',
	`create_date` DATETIME NOT NULL COMMENT '账单日期',
	`amount` DECIMAL(10,2) NOT NULL COMMENT '充值/消费金额',
	`balances` DECIMAL(10,2) NOT NULL COMMENT '当前账户余额',
	`invoice_state` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '发票状态：0-未开票 1-已开票 2-不开票',
	`invoice_id` INT(11) NOT NULL DEFAULT '0' COMMENT '发票编号',
	PRIMARY KEY (`id`)
)
COMMENT='充值账单表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;


/*--修改消费表--*/
ALTER TABLE `tb_expenses`
	CHANGE COLUMN `price` `amount` DECIMAL(10,2) NOT NULL COMMENT '消费金额' AFTER `item_type`,
	CHANGE COLUMN `remark` `remark` VARCHAR(250) NULL COMMENT '备注' AFTER `create_date`,
	ADD COLUMN `balances` DECIMAL(10,2) NULL DEFAULT NULL COMMENT '当前余额' AFTER `remark`;


