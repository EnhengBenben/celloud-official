/*!40101 SET NAMES utf8 */;

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
