/*!40101 SET NAMES utf8 */;
truncate table tb_tag;
truncate table tb_app_tag_relat;
truncate table tb_file_tag_relat;

insert into tb_tag (tag_name) values ('百菌探');
insert into tb_tag (tag_name) values ('华木兰');
insert into tb_tag (tag_name) select app_name from tb_app where off_line =  0 and (company_id=6 or company_id = 3 or company_id=48 or company_id = 45 or company_id=943 or company_id = 33) and app_id != 118;
insert into tb_tag (tag_name) select app_name from tb_app where off_line =  0 and company_id=0 and run_type!=0;

insert into tb_app_tag_relat (app_id,tag_id) values (118,1);
insert into tb_app_tag_relat (app_id,tag_id) select app_id,tag_id from tb_app a,tb_tag t where a.app_name=t.tag_name;

insert into tb_file_tag_relat (file_id,tag_id) select r.file_id,t.tag_id from (select file_id,app_id from (select a.file_id,a.app_id,count(app_id) as app from (select file_id,app_id,count(*) from tb_report group by file_id,app_id) as a group by a.file_id ) b where b.app=1) r,tb_app_tag_relat t where r.app_id=t.app_id;

