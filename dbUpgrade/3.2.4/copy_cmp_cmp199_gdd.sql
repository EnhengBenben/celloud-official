/*!40101 SET NAMES utf8 */;
INSERT INTO `tb_app` (`app_id`, `app_name`, `english_name`, `address`, `picture_name`, `create_date`, `intro`, `description`, `company_id`, `attribute`, `run_type`, `flag`, `run_data`, `data_num`, `param`, `app_doc`, `off_line`, `command`, `title`, `method`, `max_task`) VALUES (126, 'AccuSeqα', NULL, '', 'cmp.png', '2016-08-03 16:38:43', '分析50个癌基因和肿瘤抑制基因的“热点”区域2800个cosmic突变区域的关键突变位点', '', 943, 1, 1, 0, NULL, 2, NULL, '<p style="text-indent:2em;">肿瘤的分子检测对于肿瘤的早期发现、个体化治疗和预后监控都有重要的意义。肿瘤热点突变检测是针对50个癌基因和肿瘤抑制基因的&ldquo;热点&rdquo;区域2800个cosmic突变区域的关键突变位点进行检测。</p>', 0, 'nohup perl /share/biosoft/perl/PGS_MG/bin/moniter_qsub_url-v1.pl nohup perl /share/biosoft/perl/qsub/qsub_50gene.pl ${list} ${path} ProjectID${projectId} &>${path}${projectId}.log ', 'dataKey	文件名称	共获得有效片段	平均质量	平均GC含量	平均覆盖度', 'CMP', 0);
INSERT INTO `tb_app` (`app_id`, `app_name`, `english_name`, `address`, `picture_name`, `create_date`, `intro`, `description`, `company_id`, `attribute`, `run_type`, `flag`, `run_data`, `data_num`, `param`, `app_doc`, `off_line`, `command`, `title`, `method`, `max_task`) VALUES (127, 'AccuSeqα199', NULL, '', 'cmp.png', '2016-08-03 16:38:43', '分析218个癌基因和肿瘤抑制基因的关键突变位点', '', 943, 1, 1, 0, NULL, 2, NULL, '<p style="text-indent:2em;">肿瘤的分子检测对于肿瘤的早期发现、个体化治疗和预后监控都有重要的意义。综合肿瘤突变检测是针对218个癌基因和肿瘤抑制基因的关键突变位点进行检测。</p>', 0, 'nohup perl /share/biosoft/perl/PGS_MG/bin/moniter_qsub_url-v1.pl nohup perl /share/biosoft/perl/qsub/qsub_199gene.pl ${list} ${path} ProjectID${projectId} &>${path}${projectId}.log ', 'dataKey	文件名称	共获得有效片段	平均质量	平均GC含量	平均覆盖度', 'CMP', 0);
INSERT INTO `tb_app` (`app_id`, `app_name`, `english_name`, `address`, `picture_name`, `create_date`, `intro`, `description`, `company_id`, `attribute`, `run_type`, `flag`, `run_data`, `data_num`, `param`, `app_doc`, `off_line`, `command`, `title`, `method`, `max_task`) VALUES (128, 'AccuSeqΩ', '', '', 'GDD.png', '2016-08-03 16:38:43', '分析420种遗传代谢病相关的10999个关键突变位点', '', 943, 1, 1, 0, 0, 2, 0, '<p style="text-indent:2em;">中国每年新增出生缺陷婴儿中90%与遗传相关。有效的新生儿疾病筛查是提高出生人口素质，减少人口出生缺陷的重要措施。对420种遗传代谢病、先天性内分泌异常以及某些危害严重的遗传性疾病相关的10999个关键突变位点检测有助于患病儿童的早期诊断早期治疗。</p>', 0, 'nohup perl /share/biosoft/perl/PGS_MG/bin/moniter_qsub_url-v1.pl nohup perl /share/biosoft/perl/qsub/qsub_GDD.pl ${list} ${path} ProjectID${projectId} &>${path}${projectId}.log ', 'dataKey	文件名称	共获得有效片段	平均质量	平均GC含量	平均覆盖度', 'CMP', 0);

INSERT INTO `tb_app_classify_relat` (`classify_id`, `app_id`) VALUES ( 3, 126);
INSERT INTO `tb_app_classify_relat` (`classify_id`, `app_id`) VALUES ( 8, 126);
INSERT INTO `tb_app_classify_relat` (`classify_id`, `app_id`) VALUES ( 3, 127);
INSERT INTO `tb_app_classify_relat` (`classify_id`, `app_id`) VALUES ( 8, 127);
INSERT INTO `tb_app_classify_relat` (`classify_id`, `app_id`) VALUES ( 6, 128);
INSERT INTO `tb_app_classify_relat` (`classify_id`, `app_id`) VALUES ( 8, 128);

INSERT INTO `tb_app_format_relat` (`app_id`, `format_id`) VALUES (126, 4);
INSERT INTO `tb_app_format_relat` (`app_id`, `format_id`) VALUES (127, 4);
INSERT INTO `tb_app_format_relat` (`app_id`, `format_id`) VALUES (128, 4);

INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`)  VALUES (142, 126, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`)  VALUES (142, 127, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`)  VALUES (142, 128, 0);