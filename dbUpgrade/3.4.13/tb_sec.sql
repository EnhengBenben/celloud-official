/*!40101 SET NAMES utf8 */;

INSERT INTO `tb_sec_resource` (`name`, `type`, `priority`, `parent_id`, `permission`, `disabled`, `create_date`) VALUES ('语言切换', 'menu', 1, 0, 'i18n:lang', 0, now());
INSERT INTO `tb_sec_role` (`code`, `description`, `disabled`, `name`, `create_date`, `parent_id`, `mutex`, `attract`) VALUES ('i18nmanager', '国际化管理员', 0, '国际化管理员', '2017-04-13 14:05:07', 1, NULL, NULL);
