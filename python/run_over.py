#!/usr/bin/python
# -*- coding: utf-8 -*-
# python version 2.7.6

__des__='项目运行结束后的调用类'
__author__='lin'

#调用方式:python run_over.py '/share/data/webapps/Tools/upload/9/110' 'ProjectID3466'

import os
import sys
from app.PGS import PGS
from mysql.mysqlOperate import mysql
from mongo.mongoOperate import mongo

if len(sys.argv) != 3:
	print 'Usage: *.py path projectId'
	sys.exit(0)
path = sys.argv[1]
if(path.endswith(os.sep)):
	path = path[:-1]
appId = str(os.path.split(path)[1])
userId = str(os.path.split(os.path.split(path)[0])[1])
path = path+os.sep
projectId = sys.argv[2].replace('ProjectID','')

print path
print userId
print appId
print projectId

sql = "select f.file_id,f.user_id,f.data_key,f.file_name,f.size,f.another_name,u.username,u.email,c.company_id,company_name,c.state,s.software_id,s.software_name from (select * from tb_file where file_id in (select file_id from tb_data_project_relat where project_id = " + projectId + ")) as f  left join tb_user u on u.user_id = f.user_id left join (select u.user_id,c.company_id,company_name,c.state from tb_user u,tb_dept d,tb_company c where u.dept_id = d.dept_id and d.company_id = c.company_id) c on f.user_id = c.user_id left join (select r.user_id,r.software_id,s.software_name from tb_report r,tb_software s where r.project_id = 3466 and r.software_id = s.software_id) as s on f.user_id = s.user_id;"
#print sql
my=mysql.getInstance()
if my:
	result = my.query(sql)
	for i in range(len(result)):
		re = result[i] 
		#print re
		pgs = PGS.getInstance()
		if pgs:
			final = pgs.getResult(os.path.join(path,re['data_key']))
			merge = dict(final, **re)
			#print merge
			mongo = mongo.getInstance()
			objId = mongo.put(merge)
			print objId