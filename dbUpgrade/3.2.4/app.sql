INSERT INTO `tb_app` (`app_id`, `app_name`, `english_name`, `address`, `picture_name`, `create_date`, `intro`, `description`, `company_id`, `attribute`, `run_type`, `flag`, `run_data`, `data_num`, `param`, `app_doc`, `off_line`, `command`, `title`, `method`, `max_task`) VALUES (124, 'VMDA-v1.0', 'VMDA-v1.0', '', 'JBRH-PGS.png', now(), '软件是利用Miseq测序平台测序数据来筛查囊胚期细胞染色体拷贝数异常的分析软件', 'MDA、Miseq、Vazyme和PGS', 6, 1, 1, 0, 0, 1, 0, '', 0, 'nohup perl /share/biosoft/perl/qsub/moniter_qsub_phy_https_JBRH.pl perl  /share/biosoft/perl/liys/software/diagnosis/bin/PGS_pipline_vazyme/High_PPI/MDA/Run_MDA.pl ${list} ${path} /share/biosoft/perl/PGS_MG/bin/../Database/hg19.fasta  ${projectId} >${path}${projectId}.log &', 'dataName\tdataKey\tAnotherName\tTotal_Reads\tMT_ratio\tMap_Ratio(%)\tDuplicate\tGC_Count(%)\t*SD\n', 'PGS', 0);

INSERT INTO `tb_app_format_relat` (`app_id`, `format_id`) VALUES (124, 5);

INSERT INTO `tb_app_classify_relat` (`classify_id`, `app_id`) VALUES (4, 124);
INSERT INTO `tb_app_classify_relat` (`classify_id`, `app_id`) VALUES (9, 124);

INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) select user_id,124,0 from tb_user_company_relat where company_id = 6;






INSERT INTO `tb_app` (`app_id`, `app_name`, `english_name`, `address`, `picture_name`, `create_date`, `intro`, `description`, `company_id`, `attribute`, `run_type`, `flag`, `run_data`, `data_num`, `param`, `app_doc`, `off_line`, `command`, `title`, `method`, `max_task`) VALUES (125, 'VgDNA-v1.0', 'VgDNA-v1.0', '', 'JBRH-PGS.png', now(), '软件是利用Miseq测序平台测序数据来筛查组织细胞染色体拷贝数异常的分析软件', '组织、Miseq、Vazyme和PGS', 6, 1, 1, 0, 0, 1, 0, '', 0, 'nohup perl /share/biosoft/perl/qsub/moniter_qsub_phy_https_JBRH.pl perl  /share/biosoft/perl/liys/software/diagnosis/bin/PGS_pipline_vazyme/High_PPI/gDNA/Run_gDNA.pl ${list} ${path} /share/biosoft/perl/PGS_MG/bin/../Database/hg19.fasta  ${projectId} >${path}${projectId}.log &', 'dataName\tdataKey\tAnotherName\tTotal_Reads\tMT_ratio\tMap_Ratio(%)\tDuplicate\tGC_Count(%)\t*SD\n', 'PGS', 0);

INSERT INTO `tb_app_format_relat` (`app_id`, `format_id`) VALUES (125, 5);

INSERT INTO `tb_app_classify_relat` (`classify_id`, `app_id`) VALUES (4, 125);
INSERT INTO `tb_app_classify_relat` (`classify_id`, `app_id`) VALUES (9, 125);

INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) select user_id,125,0 from tb_user_company_relat where company_id = 6;