/*!40101 SET NAMES utf8 */;
INSERT INTO tb_user VALUES(NULL,'demo',md5('demo123'),'demo@celloud.cc',NULL,now(),NULL,5,'对外测试账号',0,'测试账号',0,1,NULL,1,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
INSERT INTO tb_user_company_relat VALUES(NULL,(SELECT user_id FROM tb_user WHERE role = 5),1);
INSERT INTO tb_app SELECT 138,app_name,english_name,address,picture_name,create_date,intro,description,company_id,attribute,run_type, flag,run_data, data_num, param, app_doc, off_line, command, title, method, max_task, `code` FROM tb_app WHERE app_id = 82;
INSERT INTO tb_tag(tag_id, tag_name) VALUES (45, 'Sanger');
INSERT INTO `tb_app_classify_relat` (`classify_id`, `app_id`) SELECT acr.classify_id, 138 FROM tb_app_classify_relat acr WHERE acr.app_id = 82;
INSERT INTO `tb_app_format_relat` (`app_id`, `format_id`) SELECT 138, afr.format_id FROM tb_app_format_relat afr WHERE afr.app_id = 82;
INSERT INTO `tb_app_tag_relat` (`app_id`, `tag_id`) values(138, 45);
UPDATE tb_app a1 SET a1.english_name = 'Sanger', a1.picture_name = 'Sanger.png', a1.app_name = '桑格测序数据分析', a1.company_id = 1 WHERE a1.app_id = 138;
INSERT INTO tb_app SELECT 139,app_name,english_name,address,picture_name,create_date,intro,description,company_id,attribute,run_type, flag,run_data, data_num, param, app_doc, off_line, command, title, method, max_task, `code` FROM tb_app WHERE app_id = 114;
INSERT INTO tb_tag(tag_id, tag_name) VALUES (46, 'NGS');
INSERT INTO `tb_app_classify_relat` (`classify_id`, `app_id`) SELECT acr.classify_id,139 FROM tb_app_classify_relat acr WHERE acr.app_id = 114;
INSERT INTO `tb_app_format_relat` (`app_id`, `format_id`) SELECT 139, afr.format_id FROM tb_app_format_relat afr WHERE afr.app_id = 114;
INSERT INTO `tb_app_tag_relat` (`app_id`, `tag_id`) values(139, 46);
UPDATE tb_app a1 SET a1.english_name = 'Ngs', a1.picture_name = 'Ngs.png', a1.app_name = '高通量测序数据分析', a1.company_id = 1 WHERE a1.app_id = 139;
INSERT INTO tb_user_app_right(id,user_id,app_id,is_add,auth_from) SELECT NULL,u.`user_id`,app_id,0,0 FROM tb_user u, tb_app a WHERE u.role = 5 AND a.`off_line` = 0 AND a.company_id != 0;
INSERT INTO tb_sec_user_role_relat(id,user_id,role_id,auth_from) SELECT NULL,u.`user_id`,5,0 FROM tb_user u WHERE u.role = 5;
UPDATE tb_user_app_right SET `is_add` = 1 WHERE app_id = 138 or app_id = 139;


