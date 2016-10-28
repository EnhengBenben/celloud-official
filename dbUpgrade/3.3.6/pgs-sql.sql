/*!40101 SET NAMES utf8 */;

INSERT INTO `tb_app` (`app_id`, `app_name`, `english_name`, `address`, `picture_name`, `create_date`, `intro`, `description`, `company_id`, `attribute`, `run_type`, `flag`, `run_data`, `data_num`, `param`, `app_doc`, `off_line`, `command`, `title`, `method`, `max_task`) VALUES (129, 'AmpLibrary-v1.0', 'AmpLibrary-v1.0', '', 'AmpLibrary.png', now(), '软件是利用PGM测序平台测序数据来筛选囊胚期细胞染色体拷贝数异常的分析软件', 'PGS Amplibrary ‘reduced sample volume’', 6, 1, 1, 0, 0, 1, 0, '软件是利用PGM测序平台测序数据来筛选囊胚期细胞染色体拷贝数异常的分析软件。软件是针对囊胚期细胞DNA利用AmpLibrary扩增建库一体化方法经由PGM测序平台所测得DNA数据所设计开发的分析胚胎染色体拷贝数异常的分析软件，支持BAM文件和fastq文件。软件的核心算法是隐马尔科夫模型（HMM）和核密度估计（KDE），CNV检出分辨率为4M，并且能够筛查出存在嵌合细胞。', 0, 'nohup perl /share/biosoft/perl/qsub/moniter_qsub_phy_https_JBRH.pl perl /share/biosoft/perl/liys/software/diagnosis/bin/PGS_pipline_Amplibrary_v1.0/High_PPI/Amplibrary/Run_Amplibrary.pl ${list} ${path} /share/biosoft/perl/PGS_MG/bin/../Database/hg19.fasta ${projectId} > ${path}${projectId}.log &', 'dataName\tdataKey\tAnotherName\tTotal_Reads\tMT_ratio(%)\tMap_Ratio(%)\tDuplicate(%)\tGC_Count(%)\t*SD\n', 'PGS', 0);

INSERT INTO `tb_app_format_relat` (`app_id`, `format_id`) VALUES (129, 5);

INSERT INTO `tb_app_classify_relat` (`classify_id`, `app_id`) VALUES (4, 129);
INSERT INTO `tb_app_classify_relat` (`classify_id`, `app_id`) VALUES (9, 129);

INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) select user_id, 129, 0 from tb_user_company_relat where company_id = 6;
INSERT INTO `tb_tag` (`tag_id`, `tag_name`) VALUES (37, 'AmpLibrary-v1.0');

INSERT INTO `tb_app_tag_relat` (`id`, `app_id`, `tag_id`) VALUES (30, 129, 37);