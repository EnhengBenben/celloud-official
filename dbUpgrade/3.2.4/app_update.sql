/*!40101 SET NAMES utf8 */;

update tb_app set  app_doc = '根据一代测序的多个ab1文件，转换成fasta序列格式，并多序列比对构建进化树。' where app_id=11;
update tb_app set  app_doc = '根据一代测序结果，判断11个基因的耐药位点。' where app_id=117;
update tb_app set  app_doc = '核苷酸序列快速翻译成氨基酸序列。' where app_id=73;
update tb_app set  app_doc = '根据16s序列，进行判别种属信息及溯源。' where app_id=1;
update tb_app set  app_doc = '抗结核药利福平的耐药位点检测。' where app_id=90;
update tb_app set  app_doc = '对meta 16srDNA的高通量测序结果进行分析，判断细菌的种类分布和丰度。' where app_id=114;
update tb_app set  app_doc = '对混合样本的下机数据进行拆分，根据index得到各样本的数据并进行统计。' where app_id=113;

INSERT INTO `tb_screen` (`screen_name`, `app_id`)
VALUES
	('BRAF1.png',107),
	('BRAF2.png',107),
	('BRAF3.png',107),

	('CMP1.png',110),
	('CMP2.png',110),
	('CMP3.png',110),
	('CMP4.png',110),
	('CMP5.png',110),

	('CMP1.png',111),
	('CMP2.png',111),
	('CMP3.png',111),
	('CMP4.png',111),
	('CMP5.png',111),

	('DPD1.png',106),
	('DPD2.png',106),
	('DPD3.png',106),

	('GDD1.png',112),
	('GDD2.png',112),
	('GDD3.png',112),
	('GDD4.png',112),

	('TBINH1.png',105),
	('TBINH2.png',105),
	('TBINH3.png',105),
	('TBINH4.png',105),

	('UGT1.png',108),
	('UGT2.png',108),
	('UGT3.png',108),

	('oncogene1.png',117),
	('oncogene2.png',117),
	('oncogene3.png',117)
	;

