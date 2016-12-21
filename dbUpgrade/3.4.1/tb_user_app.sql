/*!40101 SET NAMES utf8 */;

delete r.* from tb_user_app_right r,tb_user u where r.user_id = u.user_id and u.role = 1 and u.state = 0 ;

insert into tb_user_app_right (user_id,app_id,is_add,auth_from) select u.user_id,a.app_id,0,27 from tb_app a,tb_user u where a.company_id = u.company_id and u.role=1 and u.state = 0;
