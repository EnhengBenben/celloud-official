#!/usr/bin/python
# -*- coding: utf-8 -*-
# python version 2.7.6

__author__='lin'

#数据报告插入项目ID eg:python data_report_insert_projectId.py

import os,shutil,time
from mysql.mysqlOperate import mysql
from utils.FileUtils import *

ids = '9,12,15,16,18,20,21,23,24,27,28,71'
sql= 'select p.user_id,p.project_id,relat.file_id,r.app_id from tb_report r,tb_project p,tb_file_project_relat relat where p.project_id = relat.project_id and p.state=0 and p.user_id not in ('+ids+') and r.project_id = p.project_id;'
print 'total:'+sql
my=mysql.getInstance()
result = my.query(sql)
for d in result:
	q = 'select * from tb_report where user_id = '+str(d['user_id']) +' and file_id='+str(d['file_id']) +' and app_id = ' +str(d['app_id'])+' and (project_id is null or project_id =0)'
	print 'query:'+q
	r = my.query(q)
	if r:
		sql = 'update tb_report set project_id = ' + str(d['project_id']) + ',state=0 where user_id = '+str(d['user_id']) +' and file_id='+str(d['file_id']) +' and app_id = ' +str(d['app_id'])+' and (project_id is null or project_id =0)'
		print 'update:'+sql
		my.execute(sql)
		#print '--'
		#break
	else:
		sql = 'insert into tb_report (user_id,app_id,file_id,project_id,period,flag,state) values (' + str(d['user_id']) + ','+str(d['app_id'])+ ','+str(d['file_id'])+ ','+str(d['project_id'])+ ',3,0,0)'
		print 'insert:'+sql
		my.execute(sql)
		#print 'xx'
		#break
sql = 'delete from tb_file where user_id in (9,12,15,16,18,20,21,23,24,27,28,71)'
my.execute(sql)
sql = 'delete from tb_file where state = 1;'
my.execute(sql)
sql = 'delete from tb_project where state = 1;'
my.execute(sql)
sql = 'delete from tb_project where user_id in (9,12,15,16,18,20,21,23,24,27,28,71);'
my.execute(sql)
sql = 'delete from tb_file_project_relat where file_id not in (select file_id from tb_file);'
my.execute(sql)
sql = 'delete from tb_file_project_relat where project_id not in (select project_id from tb_project);'
my.execute(sql)
sql = 'delete from tb_report where user_id in (9,12,15,16,18,20,21,23,24,27,28,71);'
my.execute(sql)
sql = 'delete from tb_report where file_id not in (select file_id from tb_file);'
my.execute(sql)
sql = 'delete from tb_report where project_id not in (select project_id from tb_project);'
my.execute(sql)




