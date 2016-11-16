/*!40101 SET NAMES utf8 */;

alter table tb_sec_role add column parent_id INT(11) comment '父节点' not null;
alter table tb_sec_role add column mutex VARCHAR(30) comment '互斥' default null;
alter table tb_sec_role add column attract VARCHAR(30) comment '相吸' default null;

update tb_sec_role set mutex='report' where id = 4 or id = 5;
insert into tb_sec_role (code,description,name,create_date) values ('laboratory','独立实验室','独立实验室',now());
insert into tb_sec_role (code,description,name,create_date) values ('biocompany','生物信息公司','生物信息公司',now());

update tb_sec_role set parent_id =7 where id = 2 or id =3 ;
update tb_sec_role set parent_id =7 where id = 2 or id =3 ;
