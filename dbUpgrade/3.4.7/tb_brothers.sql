/*!40101 SET NAMES utf8 */;
/*公司信息*/
INSERT INTO tb_company(`company_id`, `company_name`, `english_name`, `state`, `create_date`, `update_date`) VALUES(954,'赛安医学基因城','GeNeCity',0,now(),now());

/*订单发送地址*/
INSERT INTO tb_company_email(`email`, `name`, `company_id`, `status`) VALUES('miaoqi@celloud.cn', '苗骐', '954', '0');

/*增加app*/
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(141,'高血压','XN1','GeNeCity.png',now(),'',954,1,0,'XN1');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(142,'冠心病','XN2','GeNeCity.png',now(),'',954,1,0,'XN2');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(143,'动脉粥样硬化','XN3','GeNeCity.png',now(),'',954,1,0,'XN3');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(144,'脑梗塞','XN4','GeNeCity.png',now(),'',954,1,0,'XN4');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(145,'脑出血','XN5','GeNeCity.png',now(),'',954,1,0,'XN5');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(146,'心肌梗塞','XN6','GeNeCity.png',now(),'',954,1,0,'XN6');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(147,'房颤','XN7','GeNeCity.png',now(),'',954,1,0,'XN7');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(148,'肥厚性心肌病','XN8','GeNeCity.png',now(),'',954,1,0,'XN8');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(149,'扩张型心肌病','XN9','GeNeCity.png',now(),'',954,1,0,'XN9');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(150,'深静脉血栓','XN10','GeNeCity.png',now(),'',954,1,0,'XN10');

INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(151,'叶酸利用能力','DX1','GeNeCity.png',now(),'',954,1,0,'DX1');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(152,'钙磷代谢能力','DX2','GeNeCity.png',now(),'',954,1,0,'DX2');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(153,'糖代谢能力','DX3','GeNeCity.png',now(),'',954,1,0,'DX3');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(154,'毒物激活能力','DX4','GeNeCity.png',now(),'',954,1,0,'DX4');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(155,'解毒能力','DX5','GeNeCity.png',now(),'',954,1,0,'DX5');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(156,'DNA损伤修复能力','DX6','GeNeCity.png',now(),'',954,1,0,'DX6');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(157,'酒精代谢能力','DX7','GeNeCity.png',now(),'',954,1,0,'DX7');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(158,'香烟代谢能力','DX8','GeNeCity.png',now(),'',954,1,0,'DX8');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(159,'抗自由基能力','DX9','GeNeCity.png',now(),'',954,1,0,'DX9');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(160,'脂质代谢能力','DX10','GeNeCity.png',now(),'',954,1,0,'DX10');

INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(161,'胃溃疡','XH1','GeNeCity.png',now(),'',954,1,0,'XH1');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(162,'酒精性肝病','XH2','GeNeCity.png',now(),'',954,1,0,'XH2');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(163,'肝纤维化','XH3','GeNeCity.png',now(),'',954,1,0,'XH3');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(164,'乙肝后肝硬化','XH4','GeNeCity.png',now(),'',954,1,0,'XH4');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(165,'原发性胆汁肝硬化','XH5','GeNeCity.png',now(),'',954,1,0,'XH5');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(166,'牙周炎','XH6','GeNeCity.png',now(),'',954,1,0,'XH6');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(167,'非酒精性脂肪肝','XH7','GeNeCity.png',now(),'',954,1,0,'XH7');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(168,'慢性胰腺炎','XH8','GeNeCity.png',now(),'',954,1,0,'XH8');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(169,'慢性乙型肝炎','XH9','GeNeCity.png',now(),'',954,1,0,'XH9');

INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(170,'肥胖(体重管理)','D01','GeNeCity.png',now(),'',954,1,0,'D01');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(171,'骨质疏松','D02','GeNeCity.png',now(),'',954,1,0,'D02');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(172,'2型糖尿病','D03','GeNeCity.png',now(),'',954,1,0,'D03');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(173,'高脂血症','D04','GeNeCity.png',now(),'',954,1,0,'D04');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(174,'痛风','D05','GeNeCity.png',now(),'',954,1,0,'D05');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(175,'甲状腺功能亢进','D06','GeNeCity.png',now(),'',954,1,0,'D06');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(176,'甲状腺功能减退','D07','GeNeCity.png',now(),'',954,1,0,'D07');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(177,'高同型半胱氨酸血症','D08','GeNeCity.png',now(),'',954,1,0,'D08');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(178,'高尿酸血症','D09','GeNeCity.png',now(),'',954,1,0,'D09');

INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(179,'系统性红斑狼疮','M01','GeNeCity.png',now(),'',954,1,0,'M01');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(180,'类风湿性关节炎','M02','GeNeCity.png',now(),'',954,1,0,'M02');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(181,'支气管哮喘','M03','GeNeCity.png',now(),'',954,1,0,'M03');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(182,'溃疡性结肠炎','M04','GeNeCity.png',now(),'',954,1,0,'M04');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(183,'强直性脊柱炎','M05','GeNeCity.png',now(),'',954,1,0,'M05');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(184,'桥本甲状腺炎','M06','GeNeCity.png',now(),'',954,1,0,'M06');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(185,'应变性鼻炎','M07','GeNeCity.png',now(),'',954,1,0,'M07');

INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(186,'忧郁症','J01','GeNeCity.png',now(),'',954,1,0,'J01');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(187,'精神分裂症','J02','GeNeCity.png',now(),'',954,1,0,'J02');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(188,'焦虑症','J03','GeNeCity.png',now(),'',954,1,0,'J03');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(189,'多发性硬化症','J04','GeNeCity.png',now(),'',954,1,0,'J04');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(190,'阿尔茨海默病','J05','GeNeCity.png',now(),'',954,1,0,'J05');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(191,'帕金森病','J06','GeNeCity.png',now(),'',954,1,0,'J06');

INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(192,'尘肺','HX1','GeNeCity.png',now(),'',954,1,0,'HX1');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(193,'石棉肺','HX2','GeNeCity.png',now(),'',954,1,0,'HX2');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(194,'肺纤维化','HX3','GeNeCity.png',now(),'',954,1,0,'HX3');

INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(195,'饮酒损伤评估','YJ7','GeNeCity.png',now(),'',954,1,0,'YJ7');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(196,'吸烟损伤评估','DX8','GeNeCity.png',now(),'',954,1,0,'DX8');

INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(197,'孕期叶酸需求','N1','GeNeCity.png',now(),'',954,1,0,'N1');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(198,'孕前/围产期营养需求','N2','GeNeCity.png',now(),'',954,1,0,'N2');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(199,'围产期风险(妊娠高血压、妊娠糖尿病、产后肥胖)','N3','GeNeCity.png',now(),'',954,1,0,'N3');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(200,'习惯性流产风险','N4','GeNeCity.png',now(),'',954,1,0,'N4');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(201,'预防药物性耳聋(孕母/儿童)','N5','GeNeCity.png',now(),'',954,1,0,'N5');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(202,'女性雌激素效能基因检测','N6','GeNeCity.png',now(),'',954,1,0,'N6');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(203,'乳腺癌基因检测','N7','GeNeCity.png',now(),'',954,1,0,'N7');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(204,'卵巢癌基因检测','N8','GeNeCity.png',now(),'',954,1,0,'N8');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(205,'宫颈癌基因检测','N9','GeNeCity.png',now(),'',954,1,0,'N9');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(206,'子宫内膜癌基因检测','N10','GeNeCity.png',now(),'',954,1,0,'N10');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(207,'女性肿瘤专项基因检测(乳腺癌、卵巢癌、宫颈癌、子宫内膜癌)','N11','GeNeCity.png',now(),'',954,1,0,'N11');

INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(208,'儿童过敏性哮喘基因检测','CH1','GeNeCity.png',now(),'',954,1,0,'CH1');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(209,'预防儿童/青春期肥胖基因检测','CH2','GeNeCity.png',now(),'',954,1,0,'CH2');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(210,'儿童骨骼发育与钙需求基因检测','CH3','GeNeCity.png',now(),'',954,1,0,'CH3');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(211,'性早熟基因检测','CH4','GeNeCity.png',now(),'',954,1,0,'CH4');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(212,'儿童白血病基因检测','CH5','GeNeCity.png',now(),'',954,1,0,'CH5');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(213,'矮身材风险与健康指导基因检测','CH6','GeNeCity.png',now(),'',954,1,0,'CH6');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(214,'儿童用药基因检测','CH8','GeNeCity.png',now(),'',954,1,0,'CH8');

INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(215,'肺腺癌','W01','GeNeCity.png',now(),'',954,1,0,'W01');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(216,'肺鳞癌','W02','GeNeCity.png',now(),'',954,1,0,'W02');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(217,'小细胞肺癌','W03','GeNeCity.png',now(),'',954,1,0,'W03');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(218,'食管癌','W04','GeNeCity.png',now(),'',954,1,0,'W04');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(219,'膀胱癌','W05','GeNeCity.png',now(),'',954,1,0,'W05');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(220,'鼻咽癌','W06','GeNeCity.png',now(),'',954,1,0,'W06');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(221,'大肠癌','W07','GeNeCity.png',now(),'',954,1,0,'W07');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(222,'肝癌','W08','GeNeCity.png',now(),'',954,1,0,'W08');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(223,'甲状腺癌','W09','GeNeCity.png',now(),'',954,1,0,'W09');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(224,'胃癌','W10','GeNeCity.png',now(),'',954,1,0,'W10');

INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(225,'胰腺癌','W11','GeNeCity.png',now(),'',954,1,0,'W11');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(226,'急性淋巴白血病','W12','GeNeCity.png',now(),'',954,1,0,'W12');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(227,'急性髓白血病','W13','GeNeCity.png',now(),'',954,1,0,'W13');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(228,'慢性髓白血病','W14','GeNeCity.png',now(),'',954,1,0,'W14');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(229,'慢性淋巴细胞白血病','W15','GeNeCity.png',now(),'',954,1,0,'W15');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(230,'骨髓增生异常综合症','W16','GeNeCity.png',now(),'',954,1,0,'W16');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(231,'多发性骨髓瘤','W17','GeNeCity.png',now(),'',954,1,0,'W17');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(232,'尿路上皮癌','W18','GeNeCity.png',now(),'',954,1,0,'W18');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(233,'皮肤基底细胞癌','W19','GeNeCity.png',now(),'',954,1,0,'W19');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(234,'恶性黑色素瘤','W20','GeNeCity.png',now(),'',954,1,0,'W20');

INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(235,'皮肤鳞状细胞癌','W21','GeNeCity.png',now(),'',954,1,0,'W21');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(236,'喉癌','W22','GeNeCity.png',now(),'',954,1,0,'W22');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(237,'口腔癌','W23','GeNeCity.png',now(),'',954,1,0,'W23');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(238,'前列腺癌','W24','GeNeCity.png',now(),'',954,1,0,'W24');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(239,'小肠癌','W25','GeNeCity.png',now(),'',954,1,0,'W25');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(240,'肾癌','W26','GeNeCity.png',now(),'',954,1,0,'W26');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(241,'胆囊癌','W27','GeNeCity.png',now(),'',954,1,0,'W27');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(242,'淋巴癌','W28','GeNeCity.png',now(),'',954,1,0,'W28');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(243,'遗传结直肠癌','W29','GeNeCity.png',now(),'',954,1,0,'W29');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(244,'脑胶质瘤','W30','GeNeCity.png',now(),'',954,1,0,'W30');

INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(245,'膳食营养健康基因检测','SY16','GeNeCity.png',now(),'',954,1,0,'SY16');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(246,'营养运动健康基因检测','YD16','GeNeCity.png',now(),'',954,1,0,'YD16');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(247,'女性美容美体28项基因体检','F28','GeNeCity.png',now(),'',954,1,0,'F28');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(248,'男性3项肿瘤','AM3','GeNeCity.png',now(),'',954,1,0,'AM3');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(249,'女性3项肿瘤','AF3','GeNeCity.png',now(),'',954,1,0,'AF3');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(250,'男性10项肿瘤易感基因检测','A10','GeNeCity.png',now(),'',954,1,0,'A10');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(251,'女性13项肿瘤易感基因检测','A13','GeNeCity.png',now(),'',954,1,0,'A13');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(252,'12项心脑血管及代谢类易感基因检测','B12','GeNeCity.png',now(),'',954,1,0,'B12');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(253,'男性22项肿瘤、心脑血管及代谢类疾病易感基因检测','CZ22','GeNeCity.png',now(),'',954,1,0,'CZ22');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(254,'女性25项肿瘤、心脑血管及代谢类疾病易感基因检测','CZ25','GeNeCity.png',now(),'',954,1,0,'CZ25');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(255,'4种肿瘤+4项健康能力基因检测','G4','GeNeCity.png',now(),'',954,1,0,'G4');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(256,'男性6种肿瘤检测+4项健康能力基因检测','M6G','GeNeCity.png',now(),'',954,1,0,'M6G');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(257,'女性7种肿瘤+4项健康能力基因检测','F7G','GeNeCity.png',now(),'',954,1,0,'F7G');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(258,'男性13项肿瘤+4项健康能力基因检测','M13G','GeNeCity.png',now(),'',954,1,0,'M13G');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(259,'女性14项肿瘤+4项健康能力基因检测','F14G','GeNeCity.png',now(),'',954,1,0,'F14G');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(260,'男性26项常见肿瘤+4项健康能力基因检测','M20+','GeNeCity.png',now(),'',954,1,0,'M20+');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(261,'女性28项常见肿瘤+4项健康能力基因检测','F20+','GeNeCity.png',now(),'',954,1,0,'F20+');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(262,'男性肿瘤31项疾病+10项心脑血管基因检测','M30X+','GeNeCity.png',now(),'',954,1,0,'M30X+');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(263,'女性肿瘤33种疾病+10项心脑血管基因检测','F30X+','GeNeCity.png',now(),'',954,1,0,'F30X+');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(264,'男性66项六大类基因检测','M60Z+','GeNeCity.png',now(),'',954,1,0,'M60Z+');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(265,'女性68种六大类基因检测','F60Z+','GeNeCity.png',now(),'',954,1,0,'F60Z+');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(266,'男性89种八大类基因检测','M88Z+','GeNeCity.png',now(),'',954,1,0,'M88Z+');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(267,'女性92种八大类基因检测','F88Z+','GeNeCity.png',now(),'',954,1,0,'F88Z+');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(268,'儿童套餐一(情商)','H1','GeNeCity.png',now(),'',954,1,0,'H1');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(269,'儿童套餐二(情商、绘画)','H2','GeNeCity.png',now(),'',954,1,0,'H2');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(270,'儿童套餐三(情商、绘画、舞蹈)','H3','GeNeCity.png',now(),'',954,1,0,'H3');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(271,'儿童套餐四(情商、绘画、舞蹈、智力)','H4','GeNeCity.png',now(),'',954,1,0,'H4');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(272,'儿童套餐五(情商、绘画、舞蹈、智力、运动)','H5','GeNeCity.png',now(),'',954,1,0,'H5');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(273,'儿童套餐六(情商、绘画、舞蹈、智力、运动、音乐)','H6','GeNeCity.png',now(),'',954,1,0,'H6');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(274,'儿童套餐七(情商、绘画、舞蹈、智力、运动、音乐、安全用药)','H7','GeNeCity.png',now(),'',954,1,0,'H7');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(275,'儿童艺术7项基因检测','C7','GeNeCity.png',now(),'',954,1,0,'C7');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(276,'儿童健康16项基因检测','C16','GeNeCity.png',now(),'',954,1,0,'C16');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(277,'儿童健康•天赋20项基因检测','C20','GeNeCity.png',now(),'',954,1,0,'C20');
INSERT INTO tb_app (`app_id`, `app_name`, `english_name`, `picture_name`, `create_date`, `description`, `company_id`, `attribute`, `run_type`, `code`) VALUES(278,'儿童健康•天赋15项基因检测','C15','GeNeCity.png',now(),'',954,1,0,'C15');

/*增加app分类*/
INSERT INTO tb_app_classify_relat SELECT NULL, 12, app_id FROM tb_app where app_id > 140;

/*增加tag*/
INSERT INTO tb_tag(tag_id, tag_name) VALUES(48, 'XN1_高血压');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(49, 'XN2_冠心病');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(50, 'XN3_动脉粥样硬化');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(51, 'XN4_脑梗塞');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(52, 'XN5_脑出血');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(53, 'XN6_心肌梗塞');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(54, 'XN7_房颤');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(55, 'XN8_肥厚性心肌病');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(56, 'XN9_扩张型心肌病');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(57, 'XN10_深静脉血栓');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(58, 'DX1_叶酸利用能力');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(59, 'DX2_钙磷代谢能力');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(60, 'DX3_糖代谢能力');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(61, 'DX4_毒物激活能力');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(62, 'DX5_解毒能力');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(63, 'DX6_DNA损伤修复能力');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(64, 'DX7_酒精代谢能力');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(65, 'DX8_香烟代谢能力');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(66, 'DX9_抗自由基能力');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(67, 'DX10_脂质代谢能力');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(68, 'XH1_胃溃疡');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(69, 'XH2_酒精性肝病');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(70, 'XH3_肝纤维化');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(71, 'XH4_乙肝后肝硬化');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(72, 'XH5_原发性胆汁肝硬化');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(73, 'XH6_牙周炎');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(74, 'XH7_非酒精性脂肪肝');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(75, 'XH8_慢性胰腺炎');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(76, 'XH9_慢性乙型肝炎');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(77, 'D01_肥胖(体重管理)');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(78, 'D02_骨质疏松');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(79, 'D03_2型糖尿病');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(80, 'D04_高脂血症');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(81, 'D05_痛风');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(82, 'D06_甲状腺功能亢进');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(83, 'D07_甲状腺功能减退');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(84, 'D08_高同型半胱氨酸血症');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(85, 'D09_高尿酸血症');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(86, 'M01_系统性红斑狼疮');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(87, 'M02_类风湿性关节炎');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(88, 'M03_支气管哮喘');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(89, 'M04_溃疡性结肠炎');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(90, 'M05_强直性脊柱炎');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(91, 'M06_桥本甲状腺炎');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(92, 'M07_应变性鼻炎');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(93, 'J01_忧郁症');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(94, 'J02_精神分裂症');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(95, 'J03_焦虑症');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(96, 'J04_多发性硬化症');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(97, 'J05_阿尔茨海默病');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(98, 'J06_帕金森病');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(99, 'HX1_尘肺');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(100, 'HX2_石棉肺');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(101, 'HX3_肺纤维化');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(102, 'YJ7_饮酒损伤评估');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(103, 'DX8_吸烟损伤评估');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(104, 'N1_孕期叶酸需求');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(105, 'N2_孕前/围产期营养需求');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(106, 'N3_围产期风险(妊娠高血压、妊娠糖尿病、产后肥胖)');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(107, 'N4_习惯性流产风险');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(108, 'N5_预防药物性耳聋(孕母/儿童)');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(109, 'N6_女性雌激素效能基因检测');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(110, 'N7_乳腺癌基因检测');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(111, 'N8_卵巢癌基因检测');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(112, 'N9_宫颈癌基因检测');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(113, 'N10_子宫内膜癌基因检测');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(114, 'N11_女性肿瘤专项基因检测(乳腺癌、卵巢癌、宫颈癌、子宫内膜癌)');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(115, 'CH1_儿童过敏性哮喘基因检测');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(116, 'CH2_预防儿童/青春期肥胖基因检测');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(117, 'CH3_儿童骨骼发育与钙需求基因检测');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(118, 'CH4_性早熟基因检测');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(119, 'CH5_儿童白血病基因检测');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(120, 'CH6_矮身材风险与健康指导基因检测');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(121, 'CH8_儿童用药基因检测');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(122, 'W01_肺腺癌');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(123, 'W02_肺鳞癌');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(124, 'W03_小细胞肺癌');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(125, 'W04_食管癌');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(126, 'W05_膀胱癌');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(127, 'W06_鼻咽癌');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(128, 'W07_大肠癌');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(129, 'W08_肝癌');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(130, 'W09_甲状腺癌');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(131, 'W10_胃癌');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(132, 'W11_胰腺癌');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(133, 'W12_急性淋巴白血病');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(134, 'W13_急性髓白血病');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(135, 'W14_慢性髓白血病');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(136, 'W15_慢性淋巴细胞白血病');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(137, 'W16_骨髓增生异常综合症');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(138, 'W17_多发性骨髓瘤');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(139, 'W18_尿路上皮癌');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(140, 'W19_皮肤基底细胞癌');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(141, 'W20_恶性黑色素瘤');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(142, 'W21_皮肤鳞状细胞癌');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(143, 'W22_喉癌');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(144, 'W23_口腔癌');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(145, 'W24_前列腺癌');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(146, 'W25_小肠癌');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(147, 'W26_肾癌');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(148, 'W27_胆囊癌');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(149, 'W28_淋巴癌');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(150, 'W29_遗传结直肠癌');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(151, 'W30_脑胶质瘤');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(152, 'SY16_膳食营养健康基因检测');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(153, 'YD16_营养运动健康基因检测');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(154, 'F28_女性美容美体28项基因体检');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(155, 'AM3_男性3项肿瘤');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(156, 'AF3_女性3项肿瘤');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(157, 'A10_男性10项肿瘤易感基因检测');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(158, 'A13_女性13项肿瘤易感基因检测');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(159, 'B12_12项心脑血管及代谢类易感基因检测');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(160, 'CZ22_男性22项肿瘤、心脑血管及代谢类疾病易感基因检测');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(161, 'CZ25_女性25项肿瘤、心脑血管及代谢类疾病易感基因检测');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(162, 'G4_4种肿瘤+4项健康能力基因检测');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(163, 'M6G_男性6种肿瘤检测+4项健康能力基因检测');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(164, 'F7G_女性7种肿瘤+4项健康能力基因检测');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(165, 'M13G_男性13项肿瘤+4项健康能力基因检测');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(166, 'F14G_女性14项肿瘤+4项健康能力基因检测');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(167, 'M20+_男性26项常见肿瘤+4项健康能力基因检测');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(168, 'F20+_女性28项常见肿瘤+4项健康能力基因检测');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(169, 'M30X+_男性肿瘤31项疾病+10项心脑血管基因检测');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(170, 'F30X+_女性肿瘤33种疾病+10项心脑血管基因检测');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(171, 'M60Z+_男性66项六大类基因检测');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(172, 'F60Z+_女性68种六大类基因检测');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(173, 'M88Z+_男性89种八大类基因检测');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(174, 'F88Z+_女性92种八大类基因检测');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(175, 'H1_儿童套餐一(情商)');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(176, 'H2_儿童套餐二(情商、绘画)');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(177, 'H3_儿童套餐三(情商、绘画、舞蹈)');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(178, 'H4_儿童套餐四(情商、绘画、舞蹈、智力)');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(179, 'H5_儿童套餐五(情商、绘画、舞蹈、智力、运动)');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(180, 'H6_儿童套餐六(情商、绘画、舞蹈、智力、运动、音乐)');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(181, 'H7_儿童套餐七(情商、绘画、舞蹈、智力、运动、音乐、安全用药)');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(182, 'C7_儿童艺术7项基因检测');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(183, 'C16_儿童健康16项基因检测');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(184, 'C20_儿童健康•天赋20项基因检测');
INSERT INTO tb_tag(tag_id, tag_name) VALUES(185, 'C15_儿童健康•天赋15项基因检测');

/*增加tag和app的关系*/
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,141,48);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,142,49);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,143,50);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,144,51);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,145,52);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,146,53);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,147,54);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,148,55);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,149,56);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,150,57);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,151,58);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,152,59);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,153,60);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,154,61);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,155,62);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,156,63);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,157,64);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,158,65);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,159,66);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,160,67);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,161,68);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,162,69);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,163,70);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,164,71);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,165,72);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,166,73);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,167,74);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,168,75);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,169,76);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,170,77);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,171,78);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,172,79);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,173,80);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,174,81);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,175,82);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,176,83);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,177,84);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,178,85);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,179,86);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,180,87);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,181,88);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,182,89);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,183,90);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,184,91);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,185,92);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,186,93);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,187,94);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,188,95);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,189,96);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,190,97);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,191,98);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,192,99);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,193,100);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,194,101);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,195,102);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,196,103);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,197,104);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,198,105);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,199,106);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,200,107);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,201,108);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,202,109);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,203,110);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,204,111);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,205,112);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,206,113);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,207,114);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,208,115);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,209,116);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,210,117);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,211,118);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,212,119);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,213,120);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,214,121);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,215,122);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,216,123);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,217,124);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,218,125);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,219,126);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,220,127);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,221,128);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,222,129);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,223,130);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,224,131);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,225,132);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,226,133);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,227,134);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,228,135);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,229,136);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,230,137);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,231,138);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,232,139);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,233,140);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,234,141);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,235,142);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,236,143);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,237,144);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,238,145);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,239,146);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,240,147);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,241,148);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,242,149);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,243,150);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,244,151);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,245,152);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,246,153);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,247,154);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,248,155);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,249,156);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,250,157);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,251,158);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,252,159);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,253,160);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,254,161);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,255,162);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,256,163);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,257,164);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,258,165);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,259,166);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,260,167);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,261,168);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,262,169);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,263,170);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,264,171);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,265,172);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,266,173);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,267,174);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,268,175);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,269,176);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,270,177);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,271,178);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,272,179);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,273,180);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,274,181);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,275,182);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,276,183);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,277,184);
INSERT INTO tb_app_tag_relat(id, app_id, tag_id) VALUES(NULL,278,185);


/*增加元数据*/
INSERT INTO tb_metadata SELECT NULL, app_id, 3, '口腔', 'KQ', 1, now(), NULL, 0 FROM tb_app WHERE app_id > 140;

/*增加病人信息表*/
CREATE TABLE `tb_patient` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `tel` varchar(11) DEFAULT NULL COMMENT '手机',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `id_card` varchar(50) DEFAULT NULL COMMENT '身份证号',
  `gender` int(1) DEFAULT NULL COMMENT '性别 0:女 1:男',
  `weight` varchar(50) DEFAULT NULL COMMENT '体重',
  `height` varchar(50) DEFAULT NULL COMMENT '身高',
  `email` varchar(50) DEFAULT NULL COMMENT '电子邮箱',
  `smoke` int(1) DEFAULT NULL COMMENT '吸烟 0:否 1:是',
  `personal_history` varchar(255) DEFAULT NULL COMMENT '个人史',
  `family_history` varchar(255) DEFAULT NULL COMMENT '家族史',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*样本增加外键列*/
ALTER TABLE tb_sample ADD COLUMN patient_id INT COMMENT 'tb_patient外键列';




