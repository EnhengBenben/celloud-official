/*!40101 SET NAMES utf8 */;
SELECT * FROM celloud.tb_app;

INSERT INTO `celloud`.`tb_app`
(`app_id`,
`app_name`,
`english_name`,
`address`,
`picture_name`,
`create_date`,
`intro`,
`description`,
`company_id`,
`attribute`,
`run_type`,
`run_data`,
`data_num`,
`param`,
`app_doc`,
`off_line`,
`command`,
`title`,
`method`,
`max_task`)
VALUES(
'123',
 'rocky',
 '', 
 '...',
 '57845fb1e4b0f6e28381f4cb.png', 
 '2016-07-12 11:10:50',
 '', 
 '', 
 '1', 
 '1', 
 '1', 
 '1', 
 '2', 
 '0', 
 '', 
 '0', 
 'perl /share/biosoft/perl/PGS_MG/bin/moniter_qsub.pl perl /share/biosoft/perl/qsub/qsub_BRCA.pl ${list} ${path} ${projectId} & > ${path}${projectId}.log ', 
 'dataKey	文件名称	种	属比对上的序列数	种序列百分比', 
 'MIB', 
 '0')