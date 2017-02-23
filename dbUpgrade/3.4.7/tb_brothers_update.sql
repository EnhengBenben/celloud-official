/*!40101 SET NAMES utf8 */;

/*新增山东兄弟公司  部门*/
INSERT INTO tb_company(`company_id`, `company_name`, `english_name`, `company_icon`, `address`, `address_en`, `zip_code`, `tel`, `state`, `create_date`, `update_date`, `province`, `city`, `address_detail`, `district`) VALUES (955,'山东兄弟', 'brothers', NULL, NULL, NULL, NULL, NULL, 0, '2017-02-21 13:43:38', NULL, '山东', '济南', NULL, NULL);
INSERT INTO `tb_dept` (`dept_name`, `english_name`, `tel`, `dept_icon`, `company_id`, `state`) VALUES ('默认部门', 'default', NULL, NULL, 954, 0);
INSERT INTO `tb_dept` (`dept_name`, `english_name`, `tel`, `dept_icon`, `company_id`, `state`) VALUES ('默认部门', 'default', NULL, NULL, 955, 0);

update tb_company_email set email='lihuihuan@celloud.cn' where company_id=954;
update tb_metadata set seq='ORAL' where name='口腔';
