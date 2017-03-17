/*!40101 SET NAMES utf8 */;
ALTER TABLE `tb_access_key` ADD COLUMN state INT DEFAULT 0 COMMENT '状态:0-启用, 1-禁用' AFTER key_secret;
