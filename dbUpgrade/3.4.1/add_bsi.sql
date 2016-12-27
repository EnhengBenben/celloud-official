/*!40101 SET NAMES utf8 */;
INSERT INTO `tb_app` (`app_id`, `app_name`, `english_name`, `address`, `picture_name`, `create_date`, `intro`, `description`, `company_id`, `attribute`, `run_type`, `flag`, `run_data`, `data_num`, `param`, `app_doc`, `off_line`, `command`, `title`, `method`, `max_task`, `code`) VALUES (133, '百菌探-腹水', 'ASB', NULL, 'bactive.png', '2016-12-26 16:07:23', '血流感染-腹水', '', 45, 1, 1, 0, 0, 1, 0, NULL, 0, 'nohup perl /share/biosoft/perl/wangzhen/16s-meta-pipeline/moniter_spark.pl nohup perl /share/biosoft/perl/wangzhen/16s-meta-pipeline/spark_BSI.pl ${list} ${path} ProjectID${projectId} &>${path}${projectId}.log &', 'dataKey	文件名称	种	属比对上的序列数	种序列百分比', 'MIB', 0, 'BSI');
INSERT INTO `tb_app` (`app_id`, `app_name`, `english_name`, `address`, `picture_name`, `create_date`, `intro`, `description`, `company_id`, `attribute`, `run_type`, `flag`, `run_data`, `data_num`, `param`, `app_doc`, `off_line`, `command`, `title`, `method`, `max_task`, `code`) VALUES (134, '百菌探-脑脊液', 'CFB', NULL, 'bactive.png', '2016-12-26 16:07:23', '血流感染-脑脊液', '', 45, 1, 1, 0, 0, 1, 0, NULL, 0, 'nohup perl /share/biosoft/perl/wangzhen/16s-meta-pipeline/moniter_spark.pl nohup perl /share/biosoft/perl/wangzhen/16s-meta-pipeline/spark_BSI.pl ${list} ${path} ProjectID${projectId} &>${path}${projectId}.log &', 'dataKey	文件名称	种	属比对上的序列数	种序列百分比', 'MIB', 0, 'BSI');
INSERT INTO `tb_app` (`app_id`, `app_name`, `english_name`, `address`, `picture_name`, `create_date`, `intro`, `description`, `company_id`, `attribute`, `run_type`, `flag`, `run_data`, `data_num`, `param`, `app_doc`, `off_line`, `command`, `title`, `method`, `max_task`, `code`) VALUES (135, '百菌探-阴道拭子', 'VSB', NULL, 'bactive.png', '2016-12-26 16:07:23', '血流感染-阴道拭子', '', 45, 1, 1, 0, 0, 1, 0, NULL, 0, 'nohup perl /share/biosoft/perl/wangzhen/16s-meta-pipeline/moniter_spark.pl nohup perl /share/biosoft/perl/wangzhen/16s-meta-pipeline/spark_BSI.pl ${list} ${path} ProjectID${projectId} &>${path}${projectId}.log &', 'dataKey	文件名称	种	属比对上的序列数	种序列百分比', 'MIB', 0, 'BSI');
INSERT INTO `tb_app` (`app_id`, `app_name`, `english_name`, `address`, `picture_name`, `create_date`, `intro`, `description`, `company_id`, `attribute`, `run_type`, `flag`, `run_data`, `data_num`, `param`, `app_doc`, `off_line`, `command`, `title`, `method`, `max_task`, `code`) VALUES (136, '百菌探-尿', 'URB', NULL, 'bactive.png', '2016-12-26 16:07:23', '血流感染-尿', '', 45, 1, 1, 0, 0, 1, 0, NULL, 0, 'nohup perl /share/biosoft/perl/wangzhen/16s-meta-pipeline/moniter_spark.pl nohup perl /share/biosoft/perl/wangzhen/16s-meta-pipeline/spark_BSI.pl ${list} ${path} ProjectID${projectId} &>${path}${projectId}.log &', 'dataKey	文件名称	种	属比对上的序列数	种序列百分比', 'MIB', 0, 'BSI');
INSERT INTO `tb_app` (`app_id`, `app_name`, `english_name`, `address`, `picture_name`, `create_date`, `intro`, `description`, `company_id`, `attribute`, `run_type`, `flag`, `run_data`, `data_num`, `param`, `app_doc`, `off_line`, `command`, `title`, `method`, `max_task`, `code`) VALUES (137, '百菌探-水', 'WAB', NULL, 'bactive.png', '2016-12-26 16:07:23', '血流感染-水', '', 45, 1, 1, 0, 0, 1, 0, NULL, 0, 'nohup perl /share/biosoft/perl/wangzhen/16s-meta-pipeline/moniter_spark.pl nohup perl /share/biosoft/perl/wangzhen/16s-meta-pipeline/spark_BSI.pl ${list} ${path} ProjectID${projectId} &>${path}${projectId}.log &', 'dataKey	文件名称	种	属比对上的序列数	种序列百分比', 'MIB', 0, 'BSI');

insert into tb_tag(tag_name) values('ASB');
insert into tb_tag(tag_name) values('CFB');
insert into tb_tag(tag_name) values('VSB');
insert into tb_tag(tag_name) values('URB');
insert into tb_tag(tag_name) values('WAB');
insert into tb_app_tag_relat(app_id,tag_id) values(133,40);
insert into tb_app_tag_relat(app_id,tag_id) values(134,41);
insert into tb_app_tag_relat(app_id,tag_id) values(135,42);
insert into tb_app_tag_relat(app_id,tag_id) values(136,43);
insert into tb_app_tag_relat(app_id,tag_id) values(137,44);


INSERT INTO `tb_app_classify_relat` (`classify_id`, `app_id`) VALUES ( 8, 133),( 8, 134),( 8, 135),( 8, 136),( 8, 137);
INSERT INTO `tb_app_classify_relat` (`classify_id`, `app_id`) VALUES ( 12, 133),( 12, 134),( 12, 135),( 12, 136),( 12, 137);
INSERT INTO `tb_app_format_relat` (`format_id`, `app_id`) VALUES (4, 133),(4, 134),( 4, 135),( 4, 136),( 4, 137);
INSERT INTO `tb_app_format_relat` (`format_id`, `app_id`) VALUES (2, 133),(2, 134),( 2, 135),( 2, 136),( 2, 137);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`)  VALUES (16, 133, 0),(16, 134, 0),(16, 135, 0),(16, 136, 0),(16, 137, 0);
