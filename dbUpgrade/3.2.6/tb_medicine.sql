/*!40101 SET NAMES utf8 */;
/*
Navicat MySQL Data Transfer

Source Server         : 本地mysql
Source Server Version : 50548
Source Host           : localhost:3306
Source Database       : celloud

Target Server Type    : MYSQL
Target Server Version : 50548
File Encoding         : 65001

Date: 2016-08-12 14:30:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_medicine
-- ----------------------------
DROP TABLE IF EXISTS `tb_medicine`;
CREATE TABLE `tb_medicine` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `app_id` int(11) DEFAULT NULL COMMENT '对应的app_id',
  `result` varchar(100) DEFAULT NULL COMMENT '检测结果',
  `advice` varchar(255) DEFAULT NULL COMMENT '该结果下的用药建议',
  `feature` varchar(50) DEFAULT NULL COMMENT '不同流程特殊的某个值\r\n            egfr:EGFR exon number\r\n            kras:KRAS exon number\r\n            hbv:type\r\n            hcv:Subtype\r\n            TB-INH:Gene\r\n            TB-Rifampicin:position\r\n            braf:突变类型',
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8 COMMENT='用药表';

-- ----------------------------
-- Records of tb_medicine
-- ----------------------------
INSERT INTO `tb_medicine` VALUES ('1', '84', 'G719S', '对易瑞沙和特罗凯敏感，请结合临床情况进行诊断', '18', '说明书');
INSERT INTO `tb_medicine` VALUES ('2', '84', 'G719C', '对易瑞沙和特罗凯敏感，请结合临床情况进行诊断', '18', '说明书');
INSERT INTO `tb_medicine` VALUES ('3', '84', 'G719A', '对易瑞沙和特罗凯敏感，请结合临床情况进行诊断', '18', '说明书');
INSERT INTO `tb_medicine` VALUES ('4', '84', 'V689M', '对易瑞沙和特罗凯敏感，请结合临床情况进行诊断', '18', 'nature');
INSERT INTO `tb_medicine` VALUES ('5', '84', 'N700D', '对易瑞沙和特罗凯敏感，请结合临床情况进行诊断', '18', 'nature');
INSERT INTO `tb_medicine` VALUES ('6', '84', 'E709K/Q', '对易瑞沙和特罗凯敏感，请结合临床情况进行诊断', '18', 'nature');
INSERT INTO `tb_medicine` VALUES ('7', '84', 'S720P', '对易瑞沙和特罗凯敏感，请结合临床情况进行诊断', '18', 'nature');
INSERT INTO `tb_medicine` VALUES ('8', '84', 'E746-A750del(1)', '对易瑞沙和特罗凯敏感，请结合临床情况进行诊断', '19', '说明书');
INSERT INTO `tb_medicine` VALUES ('9', '84', 'E746-T751>1', '对易瑞沙和特罗凯敏感，请结合临床情况进行诊断', '19', '说明书');
INSERT INTO `tb_medicine` VALUES ('10', '84', 'E746-A750del(2)', '对易瑞沙和特罗凯敏感，请结合临床情况进行诊断', '19', '说明书');
INSERT INTO `tb_medicine` VALUES ('11', '84', 'E746-T751del', '对易瑞沙和特罗凯敏感，请结合临床情况进行诊断', '19', '说明书');
INSERT INTO `tb_medicine` VALUES ('12', '84', 'E746-S752>V', '对易瑞沙和特罗凯敏感，请结合临床情况进行诊断', '19', '说明书');
INSERT INTO `tb_medicine` VALUES ('13', '84', 'E746-T751>A', '对易瑞沙和特罗凯敏感，请结合临床情况进行诊断', '19', '说明书');
INSERT INTO `tb_medicine` VALUES ('14', '84', 'E746-S752>A', '对易瑞沙和特罗凯敏感，请结合临床情况进行诊断', '19', '说明书');
INSERT INTO `tb_medicine` VALUES ('15', '84', 'L747-A750>P', '对易瑞沙和特罗凯敏感，请结合临床情况进行诊断', '19', '说明书');
INSERT INTO `tb_medicine` VALUES ('16', '84', 'L747-T751>Q', '对易瑞沙和特罗凯敏感，请结合临床情况进行诊断', '19', '说明书');
INSERT INTO `tb_medicine` VALUES ('17', '84', 'E746-S752>D', '对易瑞沙和特罗凯敏感，请结合临床情况进行诊断', '19', '说明书');
INSERT INTO `tb_medicine` VALUES ('18', '84', 'L747-E749del', '对易瑞沙和特罗凯敏感，请结合临床情况进行诊断', '19', '说明书');
INSERT INTO `tb_medicine` VALUES ('19', '84', 'L747-A750>P', '对易瑞沙和特罗凯敏感，请结合临床情况进行诊断', '19', '说明书');
INSERT INTO `tb_medicine` VALUES ('20', '84', 'L747-T751>P', '对易瑞沙和特罗凯敏感，请结合临床情况进行诊断', '19', '说明书');
INSERT INTO `tb_medicine` VALUES ('21', '84', 'L747-T751del', '对易瑞沙和特罗凯敏感，请结合临床情况进行诊断', '19', '说明书');
INSERT INTO `tb_medicine` VALUES ('22', '84', 'L747-S752del', '对易瑞沙和特罗凯敏感，请结合临床情况进行诊断', '19', '说明书');
INSERT INTO `tb_medicine` VALUES ('23', '84', 'L747-P753>Q', '对易瑞沙和特罗凯敏感，请结合临床情况进行诊断', '19', '说明书');
INSERT INTO `tb_medicine` VALUES ('24', '84', 'L747-T751>S', '对易瑞沙和特罗凯敏感，请结合临床情况进行诊断', '19', '说明书');
INSERT INTO `tb_medicine` VALUES ('25', '84', 'L747-T751del', '对易瑞沙和特罗凯敏感，请结合临床情况进行诊断', '19', '说明书');
INSERT INTO `tb_medicine` VALUES ('26', '84', 'L747-P753>S', '对易瑞沙和特罗凯敏感，请结合临床情况进行诊断', '19', '说明书');
INSERT INTO `tb_medicine` VALUES ('27', '84', 'D761Y', '对易瑞沙和特罗凯耐药，请结合临床情况进行诊断', '19', 'nature');
INSERT INTO `tb_medicine` VALUES ('28', '84', 'S768I', '对易瑞沙和特罗凯耐药，请结合临床情况进行诊断', '20', '说明书');
INSERT INTO `tb_medicine` VALUES ('29', '84', 'V769-D770insASV', '对易瑞沙和特罗凯耐药，请结合临床情况进行诊断', '20', '说明书');
INSERT INTO `tb_medicine` VALUES ('30', '84', 'D770-N771insG', '对易瑞沙和特罗凯耐药，请结合临床情况进行诊断', '20', '说明书');
INSERT INTO `tb_medicine` VALUES ('31', '84', 'H773-V774insH', '对易瑞沙和特罗凯耐药，请结合临床情况进行诊断', '20', '说明书');
INSERT INTO `tb_medicine` VALUES ('32', '84', 'T790M', '对易瑞沙和特罗凯耐药，请结合临床情况进行诊断', '20', '说明书');
INSERT INTO `tb_medicine` VALUES ('33', '84', 'D770-N771insNPG', '对易瑞沙和特罗凯耐药，请结合临床情况进行诊断', '20', 'nature');
INSERT INTO `tb_medicine` VALUES ('34', '84', 'D770-N771insSVQ', '对易瑞沙和特罗凯耐药，请结合临床情况进行诊断', '20', 'nature');
INSERT INTO `tb_medicine` VALUES ('35', '84', 'V769L', '对易瑞沙和特罗凯耐药，请结合临床情况进行诊断', '20', 'nature');
INSERT INTO `tb_medicine` VALUES ('36', '84', 'V765A', '对易瑞沙和特罗凯敏感，请结合临床情况进行诊断', '20', 'nature');
INSERT INTO `tb_medicine` VALUES ('37', '84', 'T783A', '对易瑞沙和特罗凯敏感，请结合临床情况进行诊断', '20', 'nature');
INSERT INTO `tb_medicine` VALUES ('38', '84', 'L858R', '对易瑞沙和特罗凯敏感，请结合临床情况进行诊断', '21', '说明书');
INSERT INTO `tb_medicine` VALUES ('39', '84', 'L861Q', '对易瑞沙和特罗凯敏感，请结合临床情况进行诊断', '21', '说明书');
INSERT INTO `tb_medicine` VALUES ('40', '84', 'N826S', '对易瑞沙和特罗凯敏感，请结合临床情况进行诊断', '21', 'nature');
INSERT INTO `tb_medicine` VALUES ('41', '84', 'A839T', '对易瑞沙和特罗凯敏感，请结合临床情况进行诊断', '21', 'nature');
INSERT INTO `tb_medicine` VALUES ('42', '84', 'K846R', '对易瑞沙和特罗凯敏感，请结合临床情况进行诊断', '21', 'nature');
INSERT INTO `tb_medicine` VALUES ('43', '84', 'G863D', '对易瑞沙和特罗凯敏感，请结合临床情况进行诊断', '21', 'nature');
INSERT INTO `tb_medicine` VALUES ('44', '84', '野生型', '检测结果野生型，不建议使用易瑞沙和特罗凯，请结合临床情况进行诊断', '', '');
INSERT INTO `tb_medicine` VALUES ('45', '84', '其他突变', '1）18,19,21对易瑞沙和特罗凯敏感，请结合临床情况进行诊断\r\n2）前端插入（ E762-Y764 ）与EGFR-TKI疾病控制有关，20外显子后端的插入（A767-C775）与EGFR-TKI耐药相关', '', '');
