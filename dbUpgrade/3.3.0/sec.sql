/*!40101 SET NAMES utf8 */;
INSERT INTO `tb_sec_resource` VALUES (3,'百菌探','product',0,0,'bsi:product',NULL,now()),(4,'乳腺癌','product',0,0,'rocky:product',NULL,now());
INSERT INTO `tb_sec_permission` VALUES (3,3,3),(4,4,4);

INSERT INTO `tb_sec_resource` (`id`, `name`, `type`, `priority`, `parent_id`, `permission`, `disabled`, `create_date`)
VALUES
	(5,'运行项目','button',0,0,'runWithProject:button',0,now()),
	(6,'运行数据','button',0,0,'runWithData:button',0,now());

INSERT INTO `tb_sec_role` (`id`, `code`, `description`, `disabled`, `name`)
VALUES
	(6,'runWithProject','传统用户运行项目',0,'运行项目'),
	(7,'runWithData','产品用户运行数据',0,'运行数据');
update tb_sec_role set create_date=now() where create_date is null;

update tb_sec_resource set create_date=now() where create_date is null;

INSERT INTO `tb_sec_permission` VALUES (5,6,5),(6,7,6);

select * from tb_sec_user_role_relat where role_id=3 or role_id=4;

insert into tb_sec_user_role_relat (user_id,role_id) select user_id,7 from tb_sec_user_role_relat where role_id=3 or role_id=4;

insert into tb_sec_user_role_relat (user_id,role_id) select user_id,6 from tb_user where user_id not in (select user_id from tb_sec_user_role_relat where role_id=3 or role_id=4) and role = 0 order by user_id;
