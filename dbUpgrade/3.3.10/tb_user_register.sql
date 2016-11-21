/*!40101 SET NAMES utf8 */;

alter table tb_user_register add column auth_from INT(11) comment '授权自' not null;
