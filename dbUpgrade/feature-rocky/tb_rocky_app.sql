/*!40101 SET NAMES utf8 */;
INSERT INTO `tb_app` (`app_id`, `app_name`, `english_name`, `address`, `picture_name`, `create_date`, `intro`, `description`, `company_id`, `attribute`, `run_type`, `flag`, `run_data`, `data_num`, `param`, `app_doc`, `off_line`, `command`, `title`, `method`, `max_task`, `code`, `cluster_id`) VALUES (280, '华木兰-白金', 'Rocky-platinum', '', 'rocky-min.png', now(), '', '', 1, 1, 1, 0, 1, 2, 0, '', 0, 'perl /ossfsbiosoft/biosoft/BC-pipeline/qsub_16sBSI_thread.pl ${list} ${path} ${projectId} 2', '基因	突变	说明', 'rocky', 5, '华木兰-白金', 1);

INSERT INTO `tb_app_format_relat` (`app_id`, `format_id`) VALUES (280, 4);

INSERT INTO `tb_app_classify_relat` (`classify_id`, `app_id`) VALUES (15, 280);

INSERT INTO `tb_tag` (`tag_id`, `tag_name`) VALUES(187, '华木兰-白金');

INSERT INTO `tb_app_tag_relat` (`id`, `app_id`, `tag_id`) VALUES(null, 280, 187);