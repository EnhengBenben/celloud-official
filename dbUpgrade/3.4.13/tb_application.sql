﻿/*!40101 SET NAMES utf8 */;

CREATE TABLE `tb_app_comment` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `app_id` int(11) DEFAULT NULL COMMENT '关联app',
  `user_id` int(11) DEFAULT NULL COMMENT '关联user',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `comment` varchar(255) DEFAULT NULL COMMENT '评论',
  `score` int(11) DEFAULT NULL COMMENT '评分(1,2,3,4,5)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


ALTER TABLE tb_app ADD COLUMN classic INT COMMENT '精选:0-否,1-是' AFTER `flag`;
ALTER TABLE tb_app ADD COLUMN version VARCHAR(255) COMMENT '版本信息' AFTER `code`;
ALTER TABLE tb_app ADD COLUMN update_date DATETIME COMMENT '更新时间' AFTER create_date;

update tb_app set flag=1,classic=1 where app_id=118 or app_id=123;

INSERT INTO `tb_app` (`app_id`, `app_name`, `english_name`, `address`, `picture_name`, `create_date`, `intro`, `description`, `company_id`, `attribute`, `run_type`, `flag`, `run_data`, `data_num`, `param`, `app_doc`, `off_line`, `command`, `title`, `method`, `max_task`, `code`, `cluster_id`) VALUES (280, '华木兰-白金', 'Rocky-platinum', '', 'rocky-min.png', now(), '', '', 1, 1, 1, 0, 1, 2, 0, '', 0, 'perl /ossfsbiosoft/biosoft/BC-pipeline/qsub_16sBSI_thread.pl ${list} ${path} ${projectId} 2', '基因	突变	说明', 'rocky', 5, '华木兰-白金', 1);

INSERT INTO `tb_app_format_relat` (`app_id`, `format_id`) VALUES (280, 4);

INSERT INTO `tb_app_classify_relat` (`classify_id`, `app_id`) VALUES (15, 280);

INSERT INTO `tb_tag` (`tag_id`, `tag_name`) VALUES(187, '华木兰-白金');

INSERT INTO `tb_app_tag_relat` (`id`, `app_id`, `tag_id`) VALUES(null, 280, 187);

TRUNCATE tb_app_classify_relat;

TRUNCATE tb_classify;

INSERT INTO tb_classify VALUES(1, '健康产品', 0, NULL);
INSERT INTO tb_classify VALUES(2, '临床产品', 0, NULL);
INSERT INTO tb_classify VALUES(3, '病原微生物检测', 2, NULL);
INSERT INTO tb_classify VALUES(4, '儿童天赋', 1, NULL);
INSERT INTO tb_classify VALUES(5, '儿童营养环境', 1, NULL);
INSERT INTO tb_classify VALUES(6, '二代测试数据APP', 2, NULL);
INSERT INTO tb_classify VALUES(7, '疾病易感基因检测', 1, NULL);
INSERT INTO tb_classify VALUES(8, '健康管理', 1, NULL);
INSERT INTO tb_classify VALUES(9, '其他', 2, NULL);
INSERT INTO tb_classify VALUES(10, '生育健康相关', 2, NULL);
INSERT INTO tb_classify VALUES(11, '一代数据测试APP', 2, NULL);
INSERT INTO tb_classify VALUES(12, '用药检测', 2, NULL);
INSERT INTO tb_classify VALUES(13, '孕前期检测', 1, NULL);
INSERT INTO tb_classify VALUES(14, '肿瘤风险检测', 2, NULL);
INSERT INTO tb_classify VALUES(15, '肿瘤风险检测', 1, NULL);
INSERT INTO tb_classify VALUES(16, '肿瘤个体化治疗', 2, NULL);
INSERT INTO tb_classify VALUES(17, '自身免疫代谢内分泌', 1, NULL);
INSERT INTO tb_classify VALUES(18, '综合类检测', 1, NULL);

INSERT INTO tb_app_classify_relat VALUES(null,3,1);
INSERT INTO tb_app_classify_relat VALUES(null,3,114);
INSERT INTO tb_app_classify_relat VALUES(null,3,118);
INSERT INTO tb_app_classify_relat VALUES(null,3,133);
INSERT INTO tb_app_classify_relat VALUES(null,3,134);
INSERT INTO tb_app_classify_relat VALUES(null,3,135);
INSERT INTO tb_app_classify_relat VALUES(null,3,136);
INSERT INTO tb_app_classify_relat VALUES(null,3,137);
INSERT INTO tb_app_classify_relat VALUES(null,4,268);
INSERT INTO tb_app_classify_relat VALUES(null,4,269);
INSERT INTO tb_app_classify_relat VALUES(null,4,270);
INSERT INTO tb_app_classify_relat VALUES(null,4,271);
INSERT INTO tb_app_classify_relat VALUES(null,4,272);
INSERT INTO tb_app_classify_relat VALUES(null,4,273);
INSERT INTO tb_app_classify_relat VALUES(null,4,274);
INSERT INTO tb_app_classify_relat VALUES(null,4,275);
INSERT INTO tb_app_classify_relat VALUES(null,4,276);
INSERT INTO tb_app_classify_relat VALUES(null,4,277);
INSERT INTO tb_app_classify_relat VALUES(null,4,278);
INSERT INTO tb_app_classify_relat VALUES(null,5,208);
INSERT INTO tb_app_classify_relat VALUES(null,5,209);
INSERT INTO tb_app_classify_relat VALUES(null,5,210);
INSERT INTO tb_app_classify_relat VALUES(null,5,211);
INSERT INTO tb_app_classify_relat VALUES(null,5,213);
INSERT INTO tb_app_classify_relat VALUES(null,5,214);
INSERT INTO tb_app_classify_relat VALUES(null,6,139);
INSERT INTO tb_app_classify_relat VALUES(null,7,141);
INSERT INTO tb_app_classify_relat VALUES(null,7,142);
INSERT INTO tb_app_classify_relat VALUES(null,7,143);
INSERT INTO tb_app_classify_relat VALUES(null,7,144);
INSERT INTO tb_app_classify_relat VALUES(null,7,145);
INSERT INTO tb_app_classify_relat VALUES(null,7,146);
INSERT INTO tb_app_classify_relat VALUES(null,7,147);
INSERT INTO tb_app_classify_relat VALUES(null,7,148);
INSERT INTO tb_app_classify_relat VALUES(null,7,149);
INSERT INTO tb_app_classify_relat VALUES(null,7,150);
INSERT INTO tb_app_classify_relat VALUES(null,8,170);
INSERT INTO tb_app_classify_relat VALUES(null,8,186);
INSERT INTO tb_app_classify_relat VALUES(null,8,187);
INSERT INTO tb_app_classify_relat VALUES(null,8,188);
INSERT INTO tb_app_classify_relat VALUES(null,8,189);
INSERT INTO tb_app_classify_relat VALUES(null,8,190);
INSERT INTO tb_app_classify_relat VALUES(null,8,191);
INSERT INTO tb_app_classify_relat VALUES(null,8,192);
INSERT INTO tb_app_classify_relat VALUES(null,8,193);
INSERT INTO tb_app_classify_relat VALUES(null,8,194);
INSERT INTO tb_app_classify_relat VALUES(null,8,195);
INSERT INTO tb_app_classify_relat VALUES(null,8,196);
INSERT INTO tb_app_classify_relat VALUES(null,8,245);
INSERT INTO tb_app_classify_relat VALUES(null,8,246);
INSERT INTO tb_app_classify_relat VALUES(null,8,247);
INSERT INTO tb_app_classify_relat VALUES(null,9,73);
INSERT INTO tb_app_classify_relat VALUES(null,9,113);
INSERT INTO tb_app_classify_relat VALUES(null,10,112);
INSERT INTO tb_app_classify_relat VALUES(null,10,119);
INSERT INTO tb_app_classify_relat VALUES(null,10,124);
INSERT INTO tb_app_classify_relat VALUES(null,10,130);
INSERT INTO tb_app_classify_relat VALUES(null,10,125);
INSERT INTO tb_app_classify_relat VALUES(null,10,120);
INSERT INTO tb_app_classify_relat VALUES(null,10,121);
INSERT INTO tb_app_classify_relat VALUES(null,10,122);
INSERT INTO tb_app_classify_relat VALUES(null,10,128);
INSERT INTO tb_app_classify_relat VALUES(null,10,129);
INSERT INTO tb_app_classify_relat VALUES(null,11,138);
INSERT INTO tb_app_classify_relat VALUES(null,12,11);
INSERT INTO tb_app_classify_relat VALUES(null,12,80);
INSERT INTO tb_app_classify_relat VALUES(null,12,82);
INSERT INTO tb_app_classify_relat VALUES(null,12,90);
INSERT INTO tb_app_classify_relat VALUES(null,12,105);
INSERT INTO tb_app_classify_relat VALUES(null,13,151);
INSERT INTO tb_app_classify_relat VALUES(null,13,197);
INSERT INTO tb_app_classify_relat VALUES(null,13,198);
INSERT INTO tb_app_classify_relat VALUES(null,13,199);
INSERT INTO tb_app_classify_relat VALUES(null,13,200);
INSERT INTO tb_app_classify_relat VALUES(null,13,201);
INSERT INTO tb_app_classify_relat VALUES(null,13,202);                                    
INSERT INTO tb_app_classify_relat VALUES(null,14,110);
INSERT INTO tb_app_classify_relat VALUES(null,14,111);
INSERT INTO tb_app_classify_relat VALUES(null,14,126);
INSERT INTO tb_app_classify_relat VALUES(null,14,127);
INSERT INTO tb_app_classify_relat VALUES(null,14,279);
INSERT INTO tb_app_classify_relat VALUES(null,14,131);                          
INSERT INTO tb_app_classify_relat VALUES(null,15,123);
INSERT INTO tb_app_classify_relat VALUES(null,15,203);
INSERT INTO tb_app_classify_relat VALUES(null,15,204);
INSERT INTO tb_app_classify_relat VALUES(null,15,205);
INSERT INTO tb_app_classify_relat VALUES(null,15,206);
INSERT INTO tb_app_classify_relat VALUES(null,15,207);
INSERT INTO tb_app_classify_relat VALUES(null,15,212);
INSERT INTO tb_app_classify_relat VALUES(null,15,215);
INSERT INTO tb_app_classify_relat VALUES(null,15,216);
INSERT INTO tb_app_classify_relat VALUES(null,15,217);
INSERT INTO tb_app_classify_relat VALUES(null,15,218);
INSERT INTO tb_app_classify_relat VALUES(null,15,219);
INSERT INTO tb_app_classify_relat VALUES(null,15,220);
INSERT INTO tb_app_classify_relat VALUES(null,15,221);
INSERT INTO tb_app_classify_relat VALUES(null,15,222);
INSERT INTO tb_app_classify_relat VALUES(null,15,223);
INSERT INTO tb_app_classify_relat VALUES(null,15,224);
INSERT INTO tb_app_classify_relat VALUES(null,15,225);
INSERT INTO tb_app_classify_relat VALUES(null,15,226);
INSERT INTO tb_app_classify_relat VALUES(null,15,227);
INSERT INTO tb_app_classify_relat VALUES(null,15,228);
INSERT INTO tb_app_classify_relat VALUES(null,15,229);
INSERT INTO tb_app_classify_relat VALUES(null,15,230);
INSERT INTO tb_app_classify_relat VALUES(null,15,231);
INSERT INTO tb_app_classify_relat VALUES(null,15,232);
INSERT INTO tb_app_classify_relat VALUES(null,15,233);
INSERT INTO tb_app_classify_relat VALUES(null,15,234);
INSERT INTO tb_app_classify_relat VALUES(null,15,235);
INSERT INTO tb_app_classify_relat VALUES(null,15,236);
INSERT INTO tb_app_classify_relat VALUES(null,15,237);
INSERT INTO tb_app_classify_relat VALUES(null,15,238);
INSERT INTO tb_app_classify_relat VALUES(null,15,239);
INSERT INTO tb_app_classify_relat VALUES(null,15,240);
INSERT INTO tb_app_classify_relat VALUES(null,15,241);
INSERT INTO tb_app_classify_relat VALUES(null,15,242);
INSERT INTO tb_app_classify_relat VALUES(null,15,243);
INSERT INTO tb_app_classify_relat VALUES(null,15,244);
INSERT INTO tb_app_classify_relat VALUES(null,15,248);
INSERT INTO tb_app_classify_relat VALUES(null,15,249);
INSERT INTO tb_app_classify_relat VALUES(null,15,250);
INSERT INTO tb_app_classify_relat VALUES(null,15,251);
INSERT INTO tb_app_classify_relat VALUES(null,16,84);
INSERT INTO tb_app_classify_relat VALUES(null,16,89);
INSERT INTO tb_app_classify_relat VALUES(null,16,106);
INSERT INTO tb_app_classify_relat VALUES(null,16,107);
INSERT INTO tb_app_classify_relat VALUES(null,16,108);
INSERT INTO tb_app_classify_relat VALUES(null,16,140);
INSERT INTO tb_app_classify_relat VALUES(null,17,152);
INSERT INTO tb_app_classify_relat VALUES(null,17,153);
INSERT INTO tb_app_classify_relat VALUES(null,17,154);
INSERT INTO tb_app_classify_relat VALUES(null,17,155);
INSERT INTO tb_app_classify_relat VALUES(null,17,156);
INSERT INTO tb_app_classify_relat VALUES(null,17,157);
INSERT INTO tb_app_classify_relat VALUES(null,17,158);
INSERT INTO tb_app_classify_relat VALUES(null,17,159);
INSERT INTO tb_app_classify_relat VALUES(null,17,160);
INSERT INTO tb_app_classify_relat VALUES(null,17,161);
INSERT INTO tb_app_classify_relat VALUES(null,17,162);
INSERT INTO tb_app_classify_relat VALUES(null,17,163);
INSERT INTO tb_app_classify_relat VALUES(null,17,164);
INSERT INTO tb_app_classify_relat VALUES(null,17,165);
INSERT INTO tb_app_classify_relat VALUES(null,17,166);
INSERT INTO tb_app_classify_relat VALUES(null,17,167);
INSERT INTO tb_app_classify_relat VALUES(null,17,168);
INSERT INTO tb_app_classify_relat VALUES(null,17,169);
INSERT INTO tb_app_classify_relat VALUES(null,17,171);
INSERT INTO tb_app_classify_relat VALUES(null,17,172);
INSERT INTO tb_app_classify_relat VALUES(null,17,173);
INSERT INTO tb_app_classify_relat VALUES(null,17,174);
INSERT INTO tb_app_classify_relat VALUES(null,17,175);
INSERT INTO tb_app_classify_relat VALUES(null,17,176);
INSERT INTO tb_app_classify_relat VALUES(null,17,177);
INSERT INTO tb_app_classify_relat VALUES(null,17,178);
INSERT INTO tb_app_classify_relat VALUES(null,17,179);
INSERT INTO tb_app_classify_relat VALUES(null,17,180);
INSERT INTO tb_app_classify_relat VALUES(null,17,181);
INSERT INTO tb_app_classify_relat VALUES(null,17,182);
INSERT INTO tb_app_classify_relat VALUES(null,17,183);
INSERT INTO tb_app_classify_relat VALUES(null,17,184);
INSERT INTO tb_app_classify_relat VALUES(null,17,185);
INSERT INTO tb_app_classify_relat VALUES(null,18,252);
INSERT INTO tb_app_classify_relat VALUES(null,18,253);
INSERT INTO tb_app_classify_relat VALUES(null,18,254);
INSERT INTO tb_app_classify_relat VALUES(null,18,255);
INSERT INTO tb_app_classify_relat VALUES(null,18,256);
INSERT INTO tb_app_classify_relat VALUES(null,18,257);
INSERT INTO tb_app_classify_relat VALUES(null,18,258);
INSERT INTO tb_app_classify_relat VALUES(null,18,259);
INSERT INTO tb_app_classify_relat VALUES(null,18,260);
INSERT INTO tb_app_classify_relat VALUES(null,18,261);
INSERT INTO tb_app_classify_relat VALUES(null,18,262);
INSERT INTO tb_app_classify_relat VALUES(null,18,263);
INSERT INTO tb_app_classify_relat VALUES(null,18,264);
INSERT INTO tb_app_classify_relat VALUES(null,18,265);
INSERT INTO tb_app_classify_relat VALUES(null,18,266);
INSERT INTO tb_app_classify_relat VALUES(null,18,267);





