/*!40101 SET NAMES utf8 */;
update tb_app set english_name = app_name;
update tb_app set english_name = 'BSI',app_name = '百菌探' where app_id = 118;
alter table tb_app add column code varchar(50);
update tb_app set code = english_name;