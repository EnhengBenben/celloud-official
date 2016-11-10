/*!40101 SET NAMES utf8 */;

INSERT INTO `tb_app` (`app_id`, `app_name`, `english_name`, `address`, `picture_name`, `create_date`, `intro`, `description`, `company_id`, `attribute`, `run_type`, `flag`, `run_data`, `data_num`, `param`, `app_doc`, `off_line`, `command`, `title`, `method`, `max_task`) VALUES (130, 'VgDNA_1M', 'VgDNA_1M', '', 'VgDNA_1M.png', now(), '���������Miseq����ƽ̨����������ɸ����֯ϸ��Ⱦɫ�忽�����쳣�ķ������', '��֯��Miseq��Vazyme��PGS', 6, 1, 1, 0, 0, 1, 0, '����������֯����ȡ��DNA����Vazyme������Miseq����ƽ̨�������õ���DNA��������Ƶķ�����̥Ⱦɫ�忽�����쳣�ķ��������֧��BAM�ļ���fastq�ļ�������ĺ����㷨����������Ʒ�ģ�ͣ�HMM���ͺ��ܶȹ��ƣ�KDE����CNV����ķֱ���Ϊ4M�������ܹ�ɸ�������Ƕ��ϸ����', 0, 'nohup perl /share/biosoft/perl/qsub/moniter_qsub_phy_https_JBRH.pl perl /share/biosoft/perl/liys/software/diagnosis/bin/PGS_pipline_gDNA_1M/High_PPI/gDNA/Run_gDNA_1M.pl ${list} ${path} /share/biosoft/perl/PGS_MG/bin/../Database/hg19.fasta ${projectId} > ${path}${projectId}.log', 'dataName\tdataKey\tAnotherName\tTotal_Reads\tMT_ratio(%)\tMap_Ratio(%)\tDuplicate(%)\tGC_Count(%)\t*SD\n', 'PGS', 0);

INSERT INTO `tb_app_format_relat` (`app_id`, `format_id`) VALUES (130, 5);

INSERT INTO `tb_app_classify_relat` (`classify_id`, `app_id`) VALUES (4, 130);
INSERT INTO `tb_app_classify_relat` (`classify_id`, `app_id`) VALUES (9, 130);

INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`) select user_id, 130, 0 from tb_user_company_relat where company_id = 6;
INSERT INTO `tb_tag` (`tag_id`, `tag_name`) VALUES (38, 'VgDNA_1M');

INSERT INTO `tb_app_tag_relat` (`id`, `app_id`, `tag_id`) VALUES (31, 130, 38);