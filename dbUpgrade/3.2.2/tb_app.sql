/*!40101 SET NAMES utf8 */;
insert into tb_app_format_relat (app_id,format_id) select app_id,4 from tb_app where company_id = 6 and off_line = 0;