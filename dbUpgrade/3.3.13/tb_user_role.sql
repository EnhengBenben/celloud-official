/*!40101 SET NAMES utf8 */;

alter table tb_sec_role add column parent_id INT(11) comment '父节点' not null;
alter table tb_sec_role add column mutex VARCHAR(30) comment '互斥' default null;
alter table tb_sec_role add column attract VARCHAR(30) comment '相吸' default null;

update tb_sec_role set mutex='report' where id = 4 or id = 5;
insert into tb_sec_role (code,description,name,create_date) values ('laboratory','独立实验室','独立实验室',now());
insert into tb_sec_role (code,description,name,create_date) values ('biocompany','生物信息公司','生物信息公司',now());
insert into tb_sec_role (code,description,name,create_date) values ('End-User','终端用户','终端用户',now());


update tb_sec_role set parent_id =7 where id = 2 or id =3 ;
update tb_sec_role set parent_id =7 where id = 2 or id =3 ;
update tb_sec_role set parent_id =9 where id = 6;


alter table tb_user_register add column auth_from INT(11) comment '授权自' not null;


alter table tb_user_app_right add column auth_from INT(11) comment '授权自' not null;
update tb_user_app_right B,(select r.user_id,u.user_id as f
from tb_user_app_right r,tb_user_company_relat a,tb_user u
where 
r.user_id = a.user_id and a.company_id = u.company_id and u.role = 1) A
set B.auth_from =  A.f
where B.user_id = A.user_id;
delete from tb_user_app_right where user_id = 27;
update tb_user_app_right set auth_from = 27 where user_id  in (select user_id from tb_user where role = 1);


alter table tb_sec_user_role_relat add column auth_from INT(11) comment '授权自' not null;
update tb_sec_user_role_relat B,(select r.user_id,u.user_id as f
from tb_user_app_right r,tb_user_company_relat a,tb_user u
where 
r.user_id = a.user_id and a.company_id = u.company_id and u.role = 1) A
set B.auth_from =  A.f
where B.user_id = A.user_id;
delete from tb_sec_user_role_relat where user_id = 27;
update tb_sec_user_role_relat set auth_from = 27 where user_id  in (select user_id from tb_user where role = 1);



/*删除历史表*/
truncate table tb_sec_resource;
truncate table tb_sec_role;
truncate table tb_sec_permission;


/*插入资源数据*/
INSERT INTO `tb_sec_resource` VALUES ('1', '样本管理', 'menu', '1', '0', 'sample:manage', '0', '2016-12-06 11:28:15');
INSERT INTO `tb_sec_resource` VALUES ('2', '样本采集', 'menu', '1', '1', 'sample:collection', '0', '2016-12-06 11:29:00');
INSERT INTO `tb_sec_resource` VALUES ('3', '样本跟踪', 'menu', '2', '1', 'sample:tracking', '0', '2016-12-06 11:29:45');
INSERT INTO `tb_sec_resource` VALUES ('4', '实验管理', 'menu', '2', '0', 'experiment:manage', '0', '2016-12-06 11:30:43');
INSERT INTO `tb_sec_resource` VALUES ('5', '样本入库', 'menu', '1', '4', 'sample:storage', '0', '2016-12-06 11:31:17');
INSERT INTO `tb_sec_resource` VALUES ('6', '提取DNA', 'menu', '2', '4', 'extract:dna', '0', '2016-12-06 12:13:55');
INSERT INTO `tb_sec_resource` VALUES ('7', '文库构建', 'menu', '3', '4', 'library:construction', '0', '2016-12-06 12:18:08');
INSERT INTO `tb_sec_resource` VALUES ('8', '文库列表', 'menu', '4', '4', 'library:list', '0', '2016-12-06 12:20:13');
INSERT INTO `tb_sec_resource` VALUES ('9', '数据', 'menu', '3', '0', 'data', '0', '2016-12-06 13:18:18');
INSERT INTO `tb_sec_resource` VALUES ('10', '数据看板', 'menu', '1', '9', 'data:board', '0', '2016-12-06 13:18:41');
INSERT INTO `tb_sec_resource` VALUES ('11', '数据上传', 'menu', '2', '9', 'data:upload', '0', '2016-12-06 13:19:06');
INSERT INTO `tb_sec_resource` VALUES ('12', '数据管理', 'menu', '3', '9', 'data:manage', '0', '2016-12-06 13:19:30');
INSERT INTO `tb_sec_resource` VALUES ('13', '数据运行', 'menu', '4', '9', 'data:run', '0', '2016-12-06 13:19:43');
INSERT INTO `tb_sec_resource` VALUES ('14', '报告', 'menu', '4', '0', 'report', '0', '2016-12-06 13:22:17');
INSERT INTO `tb_sec_resource` VALUES ('15', '数据报告', 'menu', '5', '14', 'data:report', '0', '2016-12-06 13:22:40');
INSERT INTO `tb_sec_resource` VALUES ('16', '项目报告', 'menu', '6', '14', 'project:report', '0', '2016-12-06 13:23:44');
INSERT INTO `tb_sec_resource` VALUES ('17', '系统设置', 'menu', '5', '0', 'system:setting', '0', '2016-12-06 13:24:07');
INSERT INTO `tb_sec_resource` VALUES ('18', '用户管理', 'menu', '1', '17', 'user:manage', '0', '2016-12-06 13:24:40');
INSERT INTO `tb_sec_resource` VALUES ('19', '基本信息', 'menu', '2', '17', 'base:info', '0', '2016-12-06 13:25:11');
INSERT INTO `tb_sec_resource` VALUES ('20', '费用中心', 'menu', '6', '0', 'cost:center', '0', '2016-12-06 13:25:32');
INSERT INTO `tb_sec_resource` VALUES ('21', '消费记录', 'menu', '1', '20', 'cost:record', '0', '2016-12-06 13:25:56');
INSERT INTO `tb_sec_resource` VALUES ('22', '账户充值', 'menu', '2', '20', 'account:recharge', '0', '2016-12-06 13:26:57');
INSERT INTO `tb_sec_resource` VALUES ('23', '充值记录', 'menu', '3', '20', 'charge:record', '0', '2016-12-06 13:27:17');
INSERT INTO `tb_sec_resource` VALUES ('24', '发票管理', 'menu', '7', '0', 'invoice:manage', '0', '2016-12-06 13:27:35');
INSERT INTO `tb_sec_resource` VALUES ('25', '应用中心', 'menu', '8', '0', 'application:center', '0', '2016-12-06 13:27:59');
INSERT INTO `tb_sec_resource` VALUES ('26', '统计', 'menu', '7', '14', 'statistics', '0', '2016-12-07 11:06:28');

/*插入角色数据*/
INSERT INTO `tb_sec_role` VALUES ('1', 'platform', '平台使用者', '0', '平台使用者', '2016-12-06 14:16:22', '0', null, null);
INSERT INTO `tb_sec_role` VALUES ('2', 'sampling', '采样人员', '0', '采样人员', '2016-12-06 14:54:14', '1', null, null);
INSERT INTO `tb_sec_role` VALUES ('3', 'experiment', '实验人员', '0', '实验人员', '2016-12-06 14:54:56', '1', null, null);
INSERT INTO `tb_sec_role` VALUES ('4', 'independentlab', '独立实验室', '0', '独立实验室', '2016-12-06 14:55:43', '1', null, null);
INSERT INTO `tb_sec_role` VALUES ('5', 'datadatareportmanager', '数据数据报告管理员', '0', '数据数据报告管理员', '2016-12-06 14:58:07', '1', null, null);
INSERT INTO `tb_sec_role` VALUES ('6', 'dataprojectreportmanager', '数据项目报告管理员', '0', '数据项目报告管理员', '2016-12-06 14:58:26', '1', null, null);
INSERT INTO `tb_sec_role` VALUES ('7', 'datamanager', '数据管理员', '0', '数据管理员', '2016-12-06 14:58:44', '1', null, null);
INSERT INTO `tb_sec_role` VALUES ('8', 'datareportmanager', '数据报告管理员', '0', '数据报告管理员', '2016-12-06 15:03:51', '1', null, null);
INSERT INTO `tb_sec_role` VALUES ('9', 'projectreportmanager', '项目报告管理员', '0', '项目报告管理员', '2016-12-06 15:04:45', '1', null, null);
INSERT INTO `tb_sec_role` VALUES ('10', 'datareportall', '数据报告ALL', '0', '数据报告ALL', '2016-12-06 15:05:04', '1', null, null);
INSERT INTO `tb_sec_role` VALUES ('11', 'projectreportall', '项目报告ALL', '0', '项目报告ALL', '2016-12-06 16:12:10', '1', null, null);
INSERT INTO `tb_sec_role` VALUES ('12', 'datadatareporthospitalmanager', '数据数据报告医院管理员', '0', '数据数据报告医院管理员', '2016-12-06 15:05:50', '1', null, null);
INSERT INTO `tb_sec_role` VALUES ('13', 'dataprojectreporthospitalmanager', '数据项目报告医院管理员', '0', '数据项目报告医院管理员', '2016-12-06 16:22:20', '1', null, null);
INSERT INTO `tb_sec_role` VALUES ('14', 'dataallhospitalmanager', '数据报告ALL医院管理员', '0', '数据报告ALL医院管理员', '2016-12-06 15:06:12', '1', null, null);
INSERT INTO `tb_sec_role` VALUES ('15', 'projectallhospitalmanager', '项目报告ALL医院管理员', '0', '项目报告ALL医院管理员', '2016-12-06 16:14:26', '1', null, null);
INSERT INTO `tb_sec_role` VALUES ('16', 'datadataprojecthospital', '数据数据报告项目报告医院管理员', '0', '数据数据报告项目报告医院管理员', '2016-12-07 10:49:20', '1', null, null);
INSERT INTO `tb_sec_role` VALUES ('17', 'datadataproject', '数据数据报告项目报告', '0', '数据数据报告项目报告', '2016-12-07 10:49:56', '1', null, null);
INSERT INTO `tb_sec_role` VALUES ('18', 'datadatasampling', '数据数据报告样本', '0', '数据数据报告样本', '2016-12-07 10:50:18', '1', null, null);

/*插入角色资源关系*/
INSERT INTO `tb_sec_permission` VALUES ('2', '3', '4');
INSERT INTO `tb_sec_permission` VALUES ('3', '3', '5');
INSERT INTO `tb_sec_permission` VALUES ('4', '3', '6');
INSERT INTO `tb_sec_permission` VALUES ('5', '3', '7');
INSERT INTO `tb_sec_permission` VALUES ('6', '3', '8');
INSERT INTO `tb_sec_permission` VALUES ('30', '7', '9');
INSERT INTO `tb_sec_permission` VALUES ('31', '7', '10');
INSERT INTO `tb_sec_permission` VALUES ('32', '7', '11');
INSERT INTO `tb_sec_permission` VALUES ('33', '7', '12');
INSERT INTO `tb_sec_permission` VALUES ('34', '7', '13');
INSERT INTO `tb_sec_permission` VALUES ('228', '5', '9');
INSERT INTO `tb_sec_permission` VALUES ('229', '5', '10');
INSERT INTO `tb_sec_permission` VALUES ('230', '5', '11');
INSERT INTO `tb_sec_permission` VALUES ('231', '5', '12');
INSERT INTO `tb_sec_permission` VALUES ('232', '5', '13');
INSERT INTO `tb_sec_permission` VALUES ('233', '5', '14');
INSERT INTO `tb_sec_permission` VALUES ('234', '5', '15');
INSERT INTO `tb_sec_permission` VALUES ('235', '5', '26');
INSERT INTO `tb_sec_permission` VALUES ('236', '6', '9');
INSERT INTO `tb_sec_permission` VALUES ('237', '6', '10');
INSERT INTO `tb_sec_permission` VALUES ('238', '6', '11');
INSERT INTO `tb_sec_permission` VALUES ('239', '6', '12');
INSERT INTO `tb_sec_permission` VALUES ('240', '6', '13');
INSERT INTO `tb_sec_permission` VALUES ('241', '6', '14');
INSERT INTO `tb_sec_permission` VALUES ('242', '6', '16');
INSERT INTO `tb_sec_permission` VALUES ('243', '6', '26');
INSERT INTO `tb_sec_permission` VALUES ('244', '8', '14');
INSERT INTO `tb_sec_permission` VALUES ('245', '8', '15');
INSERT INTO `tb_sec_permission` VALUES ('246', '8', '26');
INSERT INTO `tb_sec_permission` VALUES ('247', '9', '14');
INSERT INTO `tb_sec_permission` VALUES ('248', '9', '16');
INSERT INTO `tb_sec_permission` VALUES ('249', '9', '26');
INSERT INTO `tb_sec_permission` VALUES ('294', '12', '9');
INSERT INTO `tb_sec_permission` VALUES ('295', '12', '10');
INSERT INTO `tb_sec_permission` VALUES ('296', '12', '11');
INSERT INTO `tb_sec_permission` VALUES ('297', '12', '12');
INSERT INTO `tb_sec_permission` VALUES ('298', '12', '13');
INSERT INTO `tb_sec_permission` VALUES ('299', '12', '14');
INSERT INTO `tb_sec_permission` VALUES ('300', '12', '15');
INSERT INTO `tb_sec_permission` VALUES ('301', '12', '26');
INSERT INTO `tb_sec_permission` VALUES ('302', '12', '17');
INSERT INTO `tb_sec_permission` VALUES ('303', '12', '18');
INSERT INTO `tb_sec_permission` VALUES ('304', '12', '19');
INSERT INTO `tb_sec_permission` VALUES ('305', '13', '9');
INSERT INTO `tb_sec_permission` VALUES ('306', '13', '10');
INSERT INTO `tb_sec_permission` VALUES ('307', '13', '11');
INSERT INTO `tb_sec_permission` VALUES ('308', '13', '12');
INSERT INTO `tb_sec_permission` VALUES ('309', '13', '13');
INSERT INTO `tb_sec_permission` VALUES ('310', '13', '14');
INSERT INTO `tb_sec_permission` VALUES ('311', '13', '16');
INSERT INTO `tb_sec_permission` VALUES ('312', '13', '26');
INSERT INTO `tb_sec_permission` VALUES ('313', '13', '17');
INSERT INTO `tb_sec_permission` VALUES ('314', '13', '18');
INSERT INTO `tb_sec_permission` VALUES ('315', '13', '19');
INSERT INTO `tb_sec_permission` VALUES ('388', '16', '9');
INSERT INTO `tb_sec_permission` VALUES ('389', '16', '10');
INSERT INTO `tb_sec_permission` VALUES ('390', '16', '11');
INSERT INTO `tb_sec_permission` VALUES ('391', '16', '12');
INSERT INTO `tb_sec_permission` VALUES ('392', '16', '13');
INSERT INTO `tb_sec_permission` VALUES ('393', '16', '14');
INSERT INTO `tb_sec_permission` VALUES ('394', '16', '15');
INSERT INTO `tb_sec_permission` VALUES ('395', '16', '16');
INSERT INTO `tb_sec_permission` VALUES ('396', '16', '26');
INSERT INTO `tb_sec_permission` VALUES ('397', '16', '17');
INSERT INTO `tb_sec_permission` VALUES ('398', '16', '18');
INSERT INTO `tb_sec_permission` VALUES ('399', '16', '19');
INSERT INTO `tb_sec_permission` VALUES ('400', '17', '9');
INSERT INTO `tb_sec_permission` VALUES ('401', '17', '10');
INSERT INTO `tb_sec_permission` VALUES ('402', '17', '11');
INSERT INTO `tb_sec_permission` VALUES ('403', '17', '12');
INSERT INTO `tb_sec_permission` VALUES ('404', '17', '13');
INSERT INTO `tb_sec_permission` VALUES ('405', '17', '14');
INSERT INTO `tb_sec_permission` VALUES ('406', '17', '15');
INSERT INTO `tb_sec_permission` VALUES ('407', '17', '16');
INSERT INTO `tb_sec_permission` VALUES ('408', '17', '26');
INSERT INTO `tb_sec_permission` VALUES ('409', '18', '1');
INSERT INTO `tb_sec_permission` VALUES ('410', '18', '2');
INSERT INTO `tb_sec_permission` VALUES ('411', '18', '3');
INSERT INTO `tb_sec_permission` VALUES ('412', '18', '9');
INSERT INTO `tb_sec_permission` VALUES ('413', '18', '10');
INSERT INTO `tb_sec_permission` VALUES ('414', '18', '11');
INSERT INTO `tb_sec_permission` VALUES ('415', '18', '12');
INSERT INTO `tb_sec_permission` VALUES ('416', '18', '13');
INSERT INTO `tb_sec_permission` VALUES ('417', '18', '14');
INSERT INTO `tb_sec_permission` VALUES ('418', '18', '15');
INSERT INTO `tb_sec_permission` VALUES ('419', '18', '26');
INSERT INTO `tb_sec_permission` VALUES ('420', '2', '1');
INSERT INTO `tb_sec_permission` VALUES ('421', '2', '2');
INSERT INTO `tb_sec_permission` VALUES ('422', '2', '3');
INSERT INTO `tb_sec_permission` VALUES ('423', '4', '1');
INSERT INTO `tb_sec_permission` VALUES ('424', '4', '2');
INSERT INTO `tb_sec_permission` VALUES ('425', '4', '3');
INSERT INTO `tb_sec_permission` VALUES ('426', '4', '4');
INSERT INTO `tb_sec_permission` VALUES ('427', '4', '5');
INSERT INTO `tb_sec_permission` VALUES ('428', '4', '6');
INSERT INTO `tb_sec_permission` VALUES ('429', '4', '7');
INSERT INTO `tb_sec_permission` VALUES ('430', '4', '8');
INSERT INTO `tb_sec_permission` VALUES ('431', '10', '1');
INSERT INTO `tb_sec_permission` VALUES ('432', '10', '2');
INSERT INTO `tb_sec_permission` VALUES ('433', '10', '3');
INSERT INTO `tb_sec_permission` VALUES ('434', '10', '4');
INSERT INTO `tb_sec_permission` VALUES ('435', '10', '5');
INSERT INTO `tb_sec_permission` VALUES ('436', '10', '6');
INSERT INTO `tb_sec_permission` VALUES ('437', '10', '7');
INSERT INTO `tb_sec_permission` VALUES ('438', '10', '8');
INSERT INTO `tb_sec_permission` VALUES ('439', '10', '9');
INSERT INTO `tb_sec_permission` VALUES ('440', '10', '10');
INSERT INTO `tb_sec_permission` VALUES ('441', '10', '11');
INSERT INTO `tb_sec_permission` VALUES ('442', '10', '12');
INSERT INTO `tb_sec_permission` VALUES ('443', '10', '13');
INSERT INTO `tb_sec_permission` VALUES ('444', '10', '14');
INSERT INTO `tb_sec_permission` VALUES ('445', '10', '15');
INSERT INTO `tb_sec_permission` VALUES ('446', '10', '26');
INSERT INTO `tb_sec_permission` VALUES ('447', '10', '20');
INSERT INTO `tb_sec_permission` VALUES ('448', '10', '21');
INSERT INTO `tb_sec_permission` VALUES ('449', '10', '22');
INSERT INTO `tb_sec_permission` VALUES ('450', '10', '23');
INSERT INTO `tb_sec_permission` VALUES ('451', '10', '24');
INSERT INTO `tb_sec_permission` VALUES ('452', '10', '25');
INSERT INTO `tb_sec_permission` VALUES ('453', '11', '1');
INSERT INTO `tb_sec_permission` VALUES ('454', '11', '2');
INSERT INTO `tb_sec_permission` VALUES ('455', '11', '3');
INSERT INTO `tb_sec_permission` VALUES ('456', '11', '4');
INSERT INTO `tb_sec_permission` VALUES ('457', '11', '5');
INSERT INTO `tb_sec_permission` VALUES ('458', '11', '6');
INSERT INTO `tb_sec_permission` VALUES ('459', '11', '7');
INSERT INTO `tb_sec_permission` VALUES ('460', '11', '8');
INSERT INTO `tb_sec_permission` VALUES ('461', '11', '9');
INSERT INTO `tb_sec_permission` VALUES ('462', '11', '10');
INSERT INTO `tb_sec_permission` VALUES ('463', '11', '11');
INSERT INTO `tb_sec_permission` VALUES ('464', '11', '12');
INSERT INTO `tb_sec_permission` VALUES ('465', '11', '13');
INSERT INTO `tb_sec_permission` VALUES ('466', '11', '14');
INSERT INTO `tb_sec_permission` VALUES ('467', '11', '16');
INSERT INTO `tb_sec_permission` VALUES ('468', '11', '26');
INSERT INTO `tb_sec_permission` VALUES ('469', '11', '20');
INSERT INTO `tb_sec_permission` VALUES ('470', '11', '21');
INSERT INTO `tb_sec_permission` VALUES ('471', '11', '22');
INSERT INTO `tb_sec_permission` VALUES ('472', '11', '23');
INSERT INTO `tb_sec_permission` VALUES ('473', '11', '24');
INSERT INTO `tb_sec_permission` VALUES ('474', '11', '25');
INSERT INTO `tb_sec_permission` VALUES ('475', '14', '1');
INSERT INTO `tb_sec_permission` VALUES ('476', '14', '2');
INSERT INTO `tb_sec_permission` VALUES ('477', '14', '3');
INSERT INTO `tb_sec_permission` VALUES ('478', '14', '4');
INSERT INTO `tb_sec_permission` VALUES ('479', '14', '5');
INSERT INTO `tb_sec_permission` VALUES ('480', '14', '6');
INSERT INTO `tb_sec_permission` VALUES ('481', '14', '7');
INSERT INTO `tb_sec_permission` VALUES ('482', '14', '8');
INSERT INTO `tb_sec_permission` VALUES ('483', '14', '9');
INSERT INTO `tb_sec_permission` VALUES ('484', '14', '10');
INSERT INTO `tb_sec_permission` VALUES ('485', '14', '11');
INSERT INTO `tb_sec_permission` VALUES ('486', '14', '12');
INSERT INTO `tb_sec_permission` VALUES ('487', '14', '13');
INSERT INTO `tb_sec_permission` VALUES ('488', '14', '14');
INSERT INTO `tb_sec_permission` VALUES ('489', '14', '15');
INSERT INTO `tb_sec_permission` VALUES ('490', '14', '26');
INSERT INTO `tb_sec_permission` VALUES ('491', '14', '17');
INSERT INTO `tb_sec_permission` VALUES ('492', '14', '18');
INSERT INTO `tb_sec_permission` VALUES ('493', '14', '19');
INSERT INTO `tb_sec_permission` VALUES ('494', '14', '20');
INSERT INTO `tb_sec_permission` VALUES ('495', '14', '21');
INSERT INTO `tb_sec_permission` VALUES ('496', '14', '22');
INSERT INTO `tb_sec_permission` VALUES ('497', '14', '23');
INSERT INTO `tb_sec_permission` VALUES ('498', '14', '24');
INSERT INTO `tb_sec_permission` VALUES ('499', '14', '25');
INSERT INTO `tb_sec_permission` VALUES ('500', '15', '1');
INSERT INTO `tb_sec_permission` VALUES ('501', '15', '2');
INSERT INTO `tb_sec_permission` VALUES ('502', '15', '3');
INSERT INTO `tb_sec_permission` VALUES ('503', '15', '4');
INSERT INTO `tb_sec_permission` VALUES ('504', '15', '5');
INSERT INTO `tb_sec_permission` VALUES ('505', '15', '6');
INSERT INTO `tb_sec_permission` VALUES ('506', '15', '7');
INSERT INTO `tb_sec_permission` VALUES ('507', '15', '8');
INSERT INTO `tb_sec_permission` VALUES ('508', '15', '9');
INSERT INTO `tb_sec_permission` VALUES ('509', '15', '10');
INSERT INTO `tb_sec_permission` VALUES ('510', '15', '11');
INSERT INTO `tb_sec_permission` VALUES ('511', '15', '12');
INSERT INTO `tb_sec_permission` VALUES ('512', '15', '13');
INSERT INTO `tb_sec_permission` VALUES ('513', '15', '14');
INSERT INTO `tb_sec_permission` VALUES ('514', '15', '16');
INSERT INTO `tb_sec_permission` VALUES ('515', '15', '26');
INSERT INTO `tb_sec_permission` VALUES ('516', '15', '17');
INSERT INTO `tb_sec_permission` VALUES ('517', '15', '18');
INSERT INTO `tb_sec_permission` VALUES ('518', '15', '19');
INSERT INTO `tb_sec_permission` VALUES ('519', '15', '20');
INSERT INTO `tb_sec_permission` VALUES ('520', '15', '21');
INSERT INTO `tb_sec_permission` VALUES ('521', '15', '22');
INSERT INTO `tb_sec_permission` VALUES ('522', '15', '23');
INSERT INTO `tb_sec_permission` VALUES ('523', '15', '24');
INSERT INTO `tb_sec_permission` VALUES ('524', '15', '25');


/*更新用户角色关系*/
update tb_sec_user_role_relat set role_id = 6 where user_id in (select x.user_id from (
select user_id, GROUP_CONCAT(role_id order by role_id) roles from tb_sec_user_role_relat as a group by user_id
) as x where x.roles='1,5');

update tb_sec_user_role_relat set role_id = 5 where user_id in (select x.user_id from (
select user_id, GROUP_CONCAT(role_id order by role_id) roles from tb_sec_user_role_relat as a group by user_id
) as x where x.roles='1,4');

update tb_sec_user_role_relat set role_id = 10 where user_id in (select x.user_id from (
select user_id, GROUP_CONCAT(role_id order by role_id) roles from tb_sec_user_role_relat as a group by user_id
) as x where x.roles='1,2,3,4');

update tb_sec_user_role_relat set role_id = 16 where user_id in (select x.user_id from (
select user_id, GROUP_CONCAT(role_id order by role_id) roles from tb_sec_user_role_relat as a group by user_id
) as x where x.roles='1,4,5,6');

update tb_sec_user_role_relat set role_id = 13 where user_id in (select x.user_id from (
select user_id, GROUP_CONCAT(role_id order by role_id) roles from tb_sec_user_role_relat as a group by user_id
) as x where x.roles='1,5,6');

update tb_sec_user_role_relat set role_id = 14 where user_id in (select x.user_id from (
select user_id, GROUP_CONCAT(role_id order by role_id) roles from tb_sec_user_role_relat as a group by user_id
) as x where x.roles='1,2,3,4,6');

update tb_sec_user_role_relat set role_id = 17 where user_id in (select x.user_id from (
select user_id, GROUP_CONCAT(role_id order by role_id) roles from tb_sec_user_role_relat as a group by user_id
) as x where x.roles='1,4,5');

update tb_sec_user_role_relat set role_id = 18 where user_id in (select x.user_id from (
select user_id, GROUP_CONCAT(role_id order by role_id) roles from tb_sec_user_role_relat as a group by user_id
) as x where x.roles='1,2,4');

/*删除重复数据*/
delete from tb_sec_user_role_relat where id not in (select minid from (select min(id) minid from tb_sec_user_role_relat group by user_id) b);


