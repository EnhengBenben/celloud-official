# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 127.0.0.1 (MySQL 10.1.8-MariaDB)
# Database: celloud
# Generation Time: 2017-03-08 06:26:34 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table tb_district
# ------------------------------------------------------------

update tb_company set province = '吉林',city = '长春' where company_id = 941;
update tb_company set province = '江苏',city = '连云港' where company_id = 942;
update tb_company set province = '内蒙古自治区',city = '赤峰' where company_id = 944;
update tb_company set province = '北京',city = '大兴区' where company_id = 945;
update tb_company set province = '北京',city = '海淀区' where company_id = 946;
update tb_company set province = '山西',city = '太原' where company_id = 947;
update tb_company set province = '江苏',city = '南通' where company_id = 948;
update tb_company set province = '上海',city = '徐汇区' where company_id = 949;
update tb_company set province = '河南',city = '郑州' where company_id = 950;
update tb_company set province = '浙江',city = '永康' where company_id = 951;
update tb_company set province = '甘肃',city = '兰州' where company_id = 953;
update tb_company set province = '上海',city = '上海' where company_id = 954;
update tb_company set province = '甘肃',city = '武威' where company_id = 956;
update tb_company set province = '山西',city = '太原' where company_id = 957;
update tb_company set province = '广东',city = '深圳' where company_id = 958;
update tb_company set province = '山西',city = '太原' where company_id = 959;
update tb_company set province = '上海',city = '普陀区' where company_id = 960;

insert into tb_company (company_name,state,create_date,province) values ('立菲达安北区',0,now(),'广东');
insert into tb_dept (dept_name,english_name,company_id,state) select '默认部门','default',company_id,0 from tb_company where company_name = '立菲达安北区';
insert into tb_user (username,password,email,create_date,role,dept_id,company_id) select 'qianqian.li@thermofisher.com','a6dfca7528b0763968c36c2b19db64d9','qianqian.li@thermofisher.com',now(),4,dept_id,c.company_id from tb_dept d,tb_company c where d.company_id = c.company_id and c.company_name = '立菲达安北区';
insert into tb_user_app_right (user_id,app_id,is_add,auth_from) select (select user_id from tb_user where email = 'qianqian.li@thermofisher.com'),app_id,0,79 from tb_user_app_right where user_id = 79;
update tb_user_app_right r,(select u.user_id from tb_user u,tb_company c,tb_user_company_relat r where u.role = 0 and u.company_id = c.company_id and u.user_id = r.user_id and r.company_id = 3 and c.province in ('北京','天津','河北','黑龙江','吉林','辽宁','内蒙古自治区','陕西','甘肃','宁夏','青海','山西','河南','山东')) as p set r.auth_from = (select user_id from tb_user where email = 'qianqian.li@thermofisher.com') where r.user_id=p.user_id;
insert into tb_sec_user_role_relat (user_id,role_id,auth_from) select (select user_id from tb_user where email = 'qianqian.li@thermofisher.com'),role_id,79 from tb_sec_user_role_relat where user_id = 79;
update tb_sec_user_role_relat r,(select u.user_id from tb_user u,tb_company c,tb_user_company_relat r where u.role = 0 and u.company_id = c.company_id and u.user_id = r.user_id and r.company_id = 3 and c.province in ('北京','天津','河北','黑龙江','吉林','辽宁','内蒙古自治区','陕西','甘肃','宁夏','青海','山西','河南','山东')) as p set r.auth_from = (select user_id from tb_user where email = 'qianqian.li@thermofisher.com') where r.user_id=p.user_id;
insert into tb_user_company_relat (user_id,company_id) select user_id,3 from tb_user where email = 'qianqian.li@thermofisher.com';
update tb_user_company_relat r,(select u.user_id from tb_user u,tb_company c,tb_user_company_relat r where u.role = 0 and u.company_id = c.company_id and u.user_id = r.user_id and r.company_id = 3 and c.province in ('北京','天津','河北','黑龙江','吉林','辽宁','内蒙古自治区','陕西','甘肃','宁夏','青海','山西','河南','山东')) as p set r.company_id = (select company_id from tb_company where company_name = '立菲达安北区') where r.user_id=p.user_id;


insert into tb_company (company_name,state,create_date,province) values ('立菲达安东区',0,now(),'广东');
insert into tb_dept (dept_name,english_name,company_id,state) select '默认部门','default',company_id,0 from tb_company where company_name = '立菲达安东区';
insert into tb_user (username,password,email,create_date,role,dept_id,company_id) select 'daoliang.ma@thermofisher.com','a6dfca7528b0763968c36c2b19db64d9','daoliang.ma@thermofisher.com',now(),4,dept_id,c.company_id from tb_dept d,tb_company c where d.company_id = c.company_id and c.company_name = '立菲达安东区';
insert into tb_user_app_right (user_id,app_id,is_add,auth_from) select (select user_id from tb_user where email = 'daoliang.ma@thermofisher.com'),app_id,0,79 from tb_user_app_right where user_id = 79;
update tb_user_app_right r,(select u.user_id from tb_user u,tb_company c,tb_user_company_relat r where u.role = 0 and u.company_id = c.company_id and u.user_id = r.user_id and r.company_id = 3 and c.province in ('上海','江苏','浙江','安徽','湖南','湖北','四川','重庆','江西')) as p set r.auth_from = (select user_id from tb_user where email = 'daoliang.ma@thermofisher.com') where r.user_id=p.user_id;
insert into tb_sec_user_role_relat (user_id,role_id,auth_from) select (select user_id from tb_user where email = 'daoliang.ma@thermofisher.com'),role_id,79 from tb_sec_user_role_relat where user_id = 79;
update tb_sec_user_role_relat r,(select u.user_id from tb_user u,tb_company c,tb_user_company_relat r where u.role = 0 and u.company_id = c.company_id and u.user_id = r.user_id and r.company_id = 3 and c.province in ('上海','江苏','浙江','安徽','湖南','湖北','四川','重庆','江西')) as p set r.auth_from = (select user_id from tb_user where email = 'daoliang.ma@thermofisher.com') where r.user_id=p.user_id;
insert into tb_user_company_relat (user_id,company_id) select user_id,3 from tb_user where email = 'daoliang.ma@thermofisher.com';
update tb_user_company_relat r,(select u.user_id from tb_user u,tb_company c,tb_user_company_relat r where u.role = 0 and u.company_id = c.company_id and u.user_id = r.user_id and r.company_id = 3 and c.province in ('上海','江苏','浙江','安徽','湖南','湖北','四川','重庆','江西')) as p set r.company_id = (select company_id from tb_company where company_name = '立菲达安东区') where r.user_id=p.user_id;


insert into tb_company (company_name,state,create_date,province) values ('立菲达安南区',0,now(),'广东');
insert into tb_dept (dept_name,english_name,company_id,state) select '默认部门','default',company_id,0 from tb_company where company_name = '立菲达安南区';
insert into tb_user (username,password,email,create_date,role,dept_id,company_id) select 'Xiaohong.gan@thermofisher.com','a6dfca7528b0763968c36c2b19db64d9','Xiaohong.gan@thermofisher.com',now(),4,dept_id,c.company_id from tb_dept d,tb_company c where d.company_id = c.company_id and c.company_name = '立菲达安南区';
insert into tb_user_app_right (user_id,app_id,is_add,auth_from) select (select user_id from tb_user where email = 'Xiaohong.gan@thermofisher.com'),app_id,0,79 from tb_user_app_right where user_id = 79;
update tb_user_app_right r,(select u.user_id from tb_user u,tb_company c,tb_user_company_relat r where u.role = 0 and u.company_id = c.company_id and u.user_id = r.user_id and r.company_id = 3 and c.province in ('广东','广西','福建','云南')) as p set r.auth_from = (select user_id from tb_user where email = 'Xiaohong.gan@thermofisher.com') where r.user_id=p.user_id;
insert into tb_sec_user_role_relat (user_id,role_id,auth_from) select (select user_id from tb_user where email = 'Xiaohong.gan@thermofisher.com'),role_id,79 from tb_sec_user_role_relat where user_id = 79;
update tb_sec_user_role_relat r,(select u.user_id from tb_user u,tb_company c,tb_user_company_relat r where u.role = 0 and u.company_id = c.company_id and u.user_id = r.user_id and r.company_id = 3 and c.province in ('广东','广西','福建','云南')) as p set r.auth_from = (select user_id from tb_user where email = 'Xiaohong.gan@thermofisher.com') where r.user_id=p.user_id;
insert into tb_user_company_relat (user_id,company_id) select user_id,3 from tb_user where email = 'Xiaohong.gan@thermofisher.com';
update tb_user_company_relat r,(select u.user_id from tb_user u,tb_company c,tb_user_company_relat r where u.role = 0 and u.company_id = c.company_id and u.user_id = r.user_id and r.company_id = 3 and c.province in ('广东','广西','福建','云南')) as p set r.company_id = (select company_id from tb_company where company_name = '立菲达安南区') where r.user_id=p.user_id;

/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
