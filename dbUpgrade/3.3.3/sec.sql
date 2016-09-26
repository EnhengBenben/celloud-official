INSERT INTO `tb_sec_resource` VALUES 
(1,'我的控制台','menu',0,0,'overview:menu',0,now()),
(2,'数据上传','menu',0,0,'upload:menu',0,now()),
(3,'数据管理','product',0,0,'data:menu',0,now()),
(4,'运行计算','product',0,0,'run:button',0,now()),
(5,'统计','button',0,0,'count:menu',0,now()),
(6,'采样','button',0,0,'sampling:menu',0,now()),
(7,'实验管理','button',0,0,'experiment:menu',0,now()),
(8,'数据报告','button',0,0,'dataReport:menu',0,now()),
(9,'项目报告','button',0,0,'proReport:menu',0,now());

INSERT INTO `tb_sec_permission`(role_id,resource_id) VALUES 
(1,1),(1,2),(1,3),(1,4),(1,5),(2,6),(3,7),(4,8),(5,9);


insert into tb_sec_user_role_relat (user_id,role_id) 
select user_id,1 from tb_user where role = 0 order by user_id;

insert into tb_sec_user_role_relat (user_id,role_id) values(100,2),(100,3),(16,2),(16,3);
insert into tb_sec_user_role_relat (user_id,role_id) 
select u.user_id,4 from tb_user u,tb_user_company_relat uc 
where u.user_id=uc.user_id and uc.company_id in (45,1);
insert into tb_sec_user_role_relat (user_id,role_id) 
select u.user_id,5 from tb_user u,tb_user_company_relat uc 
where u.user_id=uc.user_id and uc.company_id not in (45,1);
select * from tb_user u,tb_user_company_relat uc 
where u.user_id=uc.user_id and u.user_id=12;


