/*!40101 SET NAMES utf8 */;
insert into tb_app(app_name, english_name, address, picture_name, create_date, intro, description, company_id, 
      attribute, run_type, flag, run_data, data_num, param, off_line, command, title, method, max_task, app_doc) 
		select * from (select app_name, english_name, address, picture_name, now(), intro, description, company_id, attribute, run_type, flag, 
      run_data, data_num, param, off_line, command, title, method, max_task, app_doc from tb_app where app_id in (110,111,112)) as b;
insert into tb_app_classify_relat(classify_id,app_id) values(3,(select app_id from tb_app where app_name='CMP' order by create_date desc limit 1,1));
insert into tb_app_classify_relat(classify_id,app_id) values(8,(select app_id from tb_app where app_name='CMP' order by create_date desc limit 1,1));
insert into tb_app_classify_relat(classify_id,app_id) values(3,(select app_id from tb_app where app_name='CMP_199' order by create_date desc limit 1,1));
insert into tb_app_classify_relat(classify_id,app_id) values(8,(select app_id from tb_app where app_name='CMP_199' order by create_date desc limit 1,1));
insert into tb_app_classify_relat(classify_id,app_id) values(6,(select app_id from tb_app where app_name='GDD' order by create_date desc limit 1,1));
insert into tb_app_classify_relat(classify_id,app_id) values(8,(select app_id from tb_app where app_name='GDD' order by create_date desc limit 1,1));

insert into tb_app_format_relat(format_id,app_id) values(4,(select app_id from tb_app where app_name='CMP' order by create_date desc limit 1,1));
insert into tb_app_format_relat(format_id,app_id) values(4,(select app_id from tb_app where app_name='CMP_199' order by create_date desc limit 1,1));
insert into tb_app_format_relat(format_id,app_id) values(4,(select app_id from tb_app where app_name='GDD' order by create_date desc limit 1,1));

