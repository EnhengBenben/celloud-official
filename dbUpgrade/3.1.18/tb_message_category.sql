/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2016/7/1 17:43:43                            */
/*==============================================================*/


drop table if exists tb_message_category;

drop table if exists tb_user_message_category_relat;

/*==============================================================*/
/* Table: tb_message_category                                   */
/*==============================================================*/
create table tb_message_category
(
   id                   int(11) not null auto_increment comment '主键',
   name                 varchar(20) comment '消息提醒分类名称',
   email                tinyint(1) comment '邮件开关 0-关 1-开',
   window               tinyint(1) comment '桌面提醒开关 0-关 1-开',
   wechat               tinyint(1) comment '微信开关 0-关 1-开',
   primary key (id)
);

alter table tb_message_category comment '消息提醒分类表';

/*==============================================================*/
/* Table: tb_user_message_category_relat                        */
/*==============================================================*/
create table tb_user_message_category_relat
(
   id                   int(11) not null auto_increment comment '主键',
   email                tinyint(1) default 0 comment '邮件开关 0-关 1-开',
   window               tinyint(1) default 0 comment '桌面提醒开关 0-关 1-开',
   wechat               tinyint(1) default 0 comment '微信开关 0-关 1开',
   user_id              int(11) comment '用户id',
   message_category_id  int(11) comment '消息提醒分类id',
   primary key (id)
);

alter table tb_user_message_category_relat comment '用户消息分类关系表';

INSERT INTO `tb_message_category` VALUES ('1', '被分享报告', '0', '0', '0');
INSERT INTO `tb_message_category` VALUES ('2', '报告生成', '0', '0', '0');
INSERT INTO `tb_message_category` VALUES ('3', '账户余额变更', '0', '0', '0');
INSERT INTO `tb_message_category` VALUES ('4', '系统公告', '0', '0', '0');

