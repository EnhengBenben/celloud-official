/*!40101 SET NAMES utf8 */;

ALTER TABLE tb_notice_user ADD INDEX user_id(user_id);
ALTER TABLE tb_notice_user ADD INDEX state(state);
ALTER TABLE tb_notice ADD INDEX type(type);
ALTER TABLE tb_notice ADD INDEX create_date(create_date);