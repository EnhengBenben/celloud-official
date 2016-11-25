/*!40101 SET NAMES utf8 */;
INSERT INTO `tb_app` (`app_id`, `app_name`, `english_name`, `address`, `picture_name`, `create_date`, `intro`, `description`, `company_id`, `attribute`, `run_type`, `flag`, `run_data`, `data_num`, `param`, `app_doc`, `off_line`, `command`, `title`, `method`, `max_task`) VALUES (131, 'AccuSeqα2', NULL, '', 'cmp.png', '2016-11-24 15:38:43', '分析50个癌基因和肿瘤抑制基因的“热点”区域2800个cosmic突变区域的关键突变位点', '', 943, 1, 1, 0, NULL, 2, NULL, '<p style="text-indent:2em;">肿瘤的分子检测对于肿瘤的早期发现、个体化治疗和预后监控都有重要的意义。肿瘤热点突变检测是针对50个癌基因和肿瘤抑制基因的&ldquo;热点&rdquo;区域2800个cosmic突变区域的关键突变位点进行检测。</p>', 0, 'nohup perl /share/biosoft/perl/PGS_MG/bin/moniter_qsub_url-v1.pl nohup perl /share/biosoft/perl/qsub/qsub_50gene_2samples.pl ${list} ${path} ProjectID${projectId} &>${path}${projectId}.log ', 'dataKey	文件名称	共获得有效片段	平均质量	平均GC含量	平均覆盖度', 'AccuSeqα2', 0);

insert into tb_tag(tag_name) values('AccuSeqα2');
insert into tb_app_tag_relat(app_id,tag_id) values(131,39);

INSERT INTO `tb_app_classify_relat` (`classify_id`, `app_id`) VALUES ( 3, 131);
INSERT INTO `tb_app_classify_relat` (`classify_id`, `app_id`) VALUES ( 8, 131);

INSERT INTO `tb_app_format_relat` (`app_id`, `format_id`) VALUES (131, 4);

INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`)  VALUES (142, 131, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`)  VALUES (9, 131, 0);