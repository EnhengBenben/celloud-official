/*!40101 SET NAMES utf8 */;

INSERT INTO `tb_app` (`app_id`, `app_name`, `english_name`, `address`, `picture_name`, `create_date`, `intro`, `description`, `company_id`, `attribute`, `run_type`, `flag`, `run_data`, `data_num`, `param`, `app_doc`, `off_line`, `command`, `title`, `method`, `max_task`,code) VALUES (140, 'FS-ocg', 'FS-ocg', '', 'FS-ocg.png',now(), '', '', 1, 1, 1, 0, NULL, 2, NULL, '', 0, '', 'Position\tRef\tVariant\tAllele Call\tFrequency\tQuality\tVariant Type\tAllele Source\tAllele Name\tGene ID\tRegion Name', 'FSocg', 0,'FS-ocg');

insert into tb_tag(tag_name) values('FSocg');
insert into tb_app_tag_relat(app_id,tag_id) select 140,max(tag_id) from tb_tag;

insert into tb_app_classify_relat (app_id,classify_id) VALUES (140,3);
insert into tb_app_classify_relat (app_id,classify_id) VALUES (140,12);

INSERT INTO `tb_file_format` (`format_id`, `format_desc`) VALUES (8,'xls文件');
insert into tb_app_format_relat (`app_id`,`format_id`) VALUES (140,8);

INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`,auth_from)  VALUES (23, 140, 0,75);