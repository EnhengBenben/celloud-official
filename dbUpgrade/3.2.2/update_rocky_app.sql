/*!40101 SET NAMES utf8 */;

UPDATE `celloud`.`tb_app` SET `command`='nohup perl /share/biosoft/perl/PGS_MG/bin/moniter_qsub_url-v1.pl nohup perl /share/biosoft/perl/qsub/qsub_BRCA.pl ${list} ${path} ${projectId} &>${path}${projectId}.log ' WHERE `app_id`='123';
UPDATE `celloud`.`tb_app` SET `title`='基因	突变	说明' WHERE `app_id`='123';
UPDATE `celloud`.`tb_app` SET `method`='rocky' WHERE `app_id`='123';


