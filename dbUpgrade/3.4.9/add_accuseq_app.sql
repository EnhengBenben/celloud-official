
/*!40101 SET NAMES utf8 */;
INSERT INTO `tb_app` (`app_id`, `app_name`, `english_name`, `address`, `picture_name`, `create_date`, `intro`, `description`, `company_id`, `attribute`, `run_type`, `flag`, `run_data`, `data_num`, `param`, `app_doc`, `off_line`, `command`, `title`, `method`, `max_task`, `code`) VALUES (279, 'AccuSeqΩ-exon', '', '', 'GDD.png', '2017-03-15 16:38:43', '分析420种遗传代谢病相关的10999个关键突变位点', '', 943, 1, 1, 0, 0, 2, 0, '<p style="text-indent:2em;">中国每年新增出生缺陷婴儿中90%与遗传相关。有效的新生儿疾病筛查是提高出生人口素质，减少人口出生缺陷的重要措施。对420种遗传代谢病、先天性内分泌异常以及某些危害严重的遗传性疾病相关的10999个关键突变位点检测有助于患病儿童的早期诊断早期治疗。</p>', 0, 'nohup perl /share/biosoft/perl/PGS_MG/bin/moniter_qsub_url-v1.pl nohup perl /share/biosoft/perl/qsub/qsub_GDD.pl ${list} ${path} ProjectID${projectId} &>${path}${projectId}.log ', 'dataKey	文件名称	共获得有效片段	平均质量	平均GC含量	平均覆盖度', 'CMP', 0, 'AccuSeqΩ');

INSERT INTO `tb_app_classify_relat` (`classify_id`, `app_id`) VALUES ( 6, 279);
INSERT INTO `tb_app_classify_relat` (`classify_id`, `app_id`) VALUES ( 8, 279);

INSERT INTO `tb_app_format_relat` (`app_id`, `format_id`) VALUES (279, 4);

INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`)  VALUES (142, 279, 0);
INSERT INTO `tb_user_app_right` (`user_id`, `app_id`, `is_add`)  VALUES (9, 279, 0);

insert into tb_tag(tag_id,tag_name) values(186,'AccuSeqΩ-exon');
insert into tb_app_tag_relat(app_id,tag_id) values(279,186);