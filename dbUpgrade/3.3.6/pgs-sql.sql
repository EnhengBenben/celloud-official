/*!40101 SET NAMES utf8 */;

INSERT INTO `tb_app` (`app_id`, `app_name`, `english_name`, `address`, `picture_name`, `create_date`, `intro`, `description`, `company_id`, `attribute`, `run_type`, `flag`, `run_data`, `data_num`, `param`, `app_doc`, `off_line`, `command`, `title`, `method`, `max_task`) VALUES (129, 'AmpLibrary-v1.0', 'AmpLibrary-v1.0', '', 'AmpLibrary.png', now(), '软件是利用PGM测序平台测序数据来筛选囊胚期细胞染色体拷贝数异常的分析软件', 'PGS Amplibrary ‘reduced sample volume’', 6, 1, 1, 0, 0, 1, 0, '软件是利用PGM测序平台测序数据来筛选囊胚期细胞染色体拷贝数异常的分析软件。软件是针对囊胚期细胞DNA利用AmpLibrary扩增建库一体化方法经由PGM测序平台所测得DNA数据所设计开发的分析胚胎染色体拷贝数异常的分析软件，支持BAM文件和fastq文件。软件的核心算法是隐马尔科夫模型（HMM）和核密度估计（KDE），CNV检出分辨率为4M，并且能够筛查出存在嵌合细胞。', 0, 'nohup perl /share/biosoft/perl/qsub/moniter_qsub_phy_https_JBRH.pl perl /share/biosoft/perl/liys/software/diagnosis/bin/PGS_pipline_Amplibrary_v1.0/High_PPI/Amplibrary/Run_Amplibrary.pl ${list} ${path} /share/biosoft/perl/PGS_MG/bin/../Database/hg19.fasta ${projectId} > ${path}${projectId}.log &', 'dataName	dataKey	AnotherName	Total_Reads	MT_ratio(%)	Map_Ratio(%)	Duplicate(%)	GC_Count(%)	*SD', 'PGS', 0);

INSERT INTO `tb_app_format_relat` (`app_id`, `format_id`) VALUES (129, 5);

INSERT INTO `tb_app_classify_relat` (`classify_id`, `app_id`) VALUES (4, 129);
INSERT INTO `tb_app_classify_relat` (`classify_id`, `app_id`) VALUES (9, 129);

INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (6, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (15, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (18, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (20, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (24, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (25, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (29, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (35, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (39, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (40, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (41, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (42, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (48, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (49, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (51, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (54, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (55, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (59, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (64, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (65, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (66, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (67, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (68, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (69, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (70, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (71, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (74, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (76, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (77, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (80, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (81, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (84, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (85, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (89, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (90, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (93, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (94, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (98, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (102, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (110, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (115, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (118, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (121, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (122, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (123, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (127, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (136, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (139, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (140, 129, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) VALUES (145, 129, 0);

INSERT INTO `tb_tag` (`tag_id`, `tag_name`) VALUES (37, 'AmpLibrary');

INSERT INTO `tb_app_tag_relat` (`id`, `app_id`, `tag_id`) VALUES (30, 129, 37);