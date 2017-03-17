/*!40101 SET NAMES utf8 */;

update tb_app set command='nohup perl /share/biosoft/perl/PGS_MG/bin/moniter_qsub_url-v1.pl nohup perl /share/biosoft/perl/qsub/qsub_GDD_pipeline_2.0.pl ${list} ${path} ProjectID${projectId} &>${path}${projectId}.log ' where app_id=279;