# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 127.0.0.1 (MySQL 10.1.8-MariaDB)
# Database: celloud
# Generation Time: 2017-03-08 06:26:34 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table tb_district
# ------------------------------------------------------------

update tb_app set command = 'nohup perl /share/biosoft/Script/Script_share/moniter_qsub_url-v1.pl nohup perl /share/biosoft/Script/CMP/qsub_50gene.pl ${list} ${path} ProjectID${projectId} &>${path}${projectId}.log' where app_id = 126;
update tb_app set command = 'nohup perl /share/biosoft/Script/Script_share/moniter_qsub_url-v1.pl nohup perl /share/biosoft/Script/CMP/qsub_50gene.pl ${list} ${path} ProjectID${projectId} &>${path}${projectId}.log' where app_id = 110;
update tb_app set command = 'nohup perl /share/biosoft/Script/Script_share/moniter_qsub_url-v1.pl nohup perl /share/biosoft/Script/CMP_199/qsub_199gene.pl ${list} ${path} ProjectID${projectId} &>${path}${projectId}.log' where app_id = 127;
update tb_app set command = 'nohup perl /share/biosoft/Script/Script_share/moniter_qsub_url-v1.pl nohup perl /share/biosoft/Script/GDD/qsub_GDD.pl ${list} ${path} ProjectID${projectId} &>${path}${projectId}.log' where app_id = 128;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
