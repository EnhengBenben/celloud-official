/*!40101 SET NAMES utf8 */;

INSERT INTO `tb_app` (`app_id`, `app_name`, `english_name`, `address`, `picture_name`, `create_date`, `intro`, `description`, `company_id`, `attribute`, `run_type`, `flag`, `run_data`, `data_num`, `param`, `app_doc`, `off_line`, `command`, `title`, `method`, `max_task`) VALUES (130, 'VgDNA_1M', 'VgDNA_1M', '', 'VgDNA_1M.png', now(), '软件是利用Miseq测序平台测序数据来筛查组织细胞染色体拷贝数异常的分析软件', '组织、Miseq、Vazyme和PGS', 6, 1, 1, 0, 0, 1, 0, '软件是针对组织中提取的DNA利用Vazyme建库由Miseq测序平台测序所得到的DNA数据所设计的分析胚胎染色体拷贝数异常的分析软件，支持BAM文件和fastq文件。软件的核心算法包括隐马尔科夫模型（HMM）和核密度估计（KDE），CNV检出的分辨率为4M，并且能够筛查出存在嵌合细胞。', 0, 'nohup perl /share/biosoft/perl/qsub/moniter_qsub_phy_https_JBRH.pl perl /share/biosoft/perl/liys/software/diagnosis/bin/PGS_pipline_gDNA_1M/High_PPI/gDNA/Run_gDNA_1M.pl ${list} ${path} /share/biosoft/perl/PGS_MG/bin/../Database/hg19.fasta ${projectId} > ${path}${projectId}.log', 'dataName\tdataKey\tAnotherName\tTotal_Reads\tMT_ratio(%)\tMap_Ratio(%)\tDuplicate(%)\tGC_Count(%)\t*SD\n', 'PGS', 0);

INSERT INTO `tb_app_format_relat` (`app_id`, `format_id`) VALUES (130, 5);

INSERT INTO `tb_app_classify_relat` (`classify_id`, `app_id`) VALUES (4, 130);
INSERT INTO `tb_app_classify_relat` (`classify_id`, `app_id`) VALUES (9, 130);

INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) select user_id, 130, 0 from tb_user_company_relat where company_id = 6;
INSERT INTO `tb_tag` (`tag_id`, `tag_name`) VALUES (38, 'VgDNA_1M');

INSERT INTO `tb_app_tag_relat` (`id`, `app_id`, `tag_id`) VALUES (31, 130, 38);