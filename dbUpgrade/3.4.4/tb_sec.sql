/*!40101 SET NAMES utf8 */;

insert into tb_sec_resource (id,name,type,priority,parent_id,permission,disabled,create_date) values (27,'华木兰产品','menu',9,0,'rocky:product',0,now());
insert into tb_sec_resource (id,name,type,priority,parent_id,permission,disabled,create_date) values (28,'华木兰上传','menu',1,27,'rocky:upload',0,now());
insert into tb_sec_resource (id,name,type,priority,parent_id,permission,disabled,create_date) values (29,'华木兰数据','menu',2,27,'rocky:data',0,now());
insert into tb_sec_resource (id,name,type,priority,parent_id,permission,disabled,create_date) values (30,'华木兰报告','menu',3,27,'rocky:report',0,now());


insert into tb_sec_role (id,code,description,name,create_date,parent_id) values (20,'rockydata','华木兰数据上传及列表','华木兰数据',now(),1);
insert into tb_sec_role (id,code,description,name,create_date,parent_id) values (21,'rockyreport','华木兰报告','华木兰报告',now(),1);
insert into tb_sec_role (id,code,description,name,create_date,parent_id) values (22,'rockyproduct','华木兰数据上传及列表及报告','华木兰产品',now(),1);

insert into tb_sec_permission (role_id,resource_id) values (20,28);
insert into tb_sec_permission (role_id,resource_id) values (20,29);
insert into tb_sec_permission (role_id,resource_id) values (21,30);
insert into tb_sec_permission (role_id,resource_id) values (22,28);
insert into tb_sec_permission (role_id,resource_id) values (22,29);
insert into tb_sec_permission (role_id,resource_id) values (22,30);

insert into tb_sec_user_role_relat (user_id,role_id,auth_from) select r.user_id,22,27 from tb_user_app_right r left join tb_user u on r.user_id = u.user_id  where app_id = 123 and u.state = 0 and role = 1;
insert into tb_sec_user_role_relat (user_id,role_id,auth_from) select r.user_id,21,27 from tb_user_app_right r left join tb_user u on r.user_id = u.user_id  where app_id = 123 and u.state = 0 and role = 1;
insert into tb_sec_user_role_relat (user_id,role_id,auth_from) select r.user_id,20,27 from tb_user_app_right r left join tb_user u on r.user_id = u.user_id  where app_id = 123 and u.state = 0 and role = 1;
insert into tb_sec_user_role_relat (user_id,role_id,auth_from) select r.user_id,22,75 from tb_user_app_right r left join tb_user u on r.user_id = u.user_id  where app_id = 123 and u.state = 0 and role = 0;