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
#appId = str(os.path.split(path)[1])
#userId = str(os.path.split(os.path.split(path)[0])[1])
path = path+os.sep
projectId = sys.argv[2].replace('ProjectID','')

sql = "select f.*,c.companyId,c.companyName,c.companyEngName,c.companyIcon,c.companyAddr,c.companyEnAddr,c.zipCode,c.companyTel,c.deptName,c.deptEngName,c.deptIcon,c.deptTel  ,s.appId,s.appName,s.createDate,s.projectId,u.username,u.email  from (select file_id as fileId,user_id as userId,data_key as dataKey,file_name as fileName,size,another_name as anotherName from tb_file where file_id in (select file_id from tb_data_project_relat where project_id = "+projectId+")) as f    left join tb_user u   on f.userId = u.user_id  left join (  select c.company_id as companyId,c.company_name as companyName,c.english_name as companyEngName,c.company_icon as companyIcon,c.address as companyAddr,c.english_name as companyEnAddr,c.zip_code as zipCode,c.tel as companyTel,d.dept_name as deptName,d.english_name as deptEngName,d.dept_icon as deptIcon,d.tel as deptTel,u.user_id as userId from tb_user u,tb_dept d,tb_company c where u.dept_id = d.dept_id and d.company_id = c.company_id) c     on f.userId = c.userId   left join (select r.user_id,r.software_id as appId,s.software_name as appName,r.create_date as createDate,r.project_id as projectId from tb_report r,tb_software s where r.project_id = "+projectId+" and r.software_id = s.software_id) as s   on f.userId = s.user_id;"
my=mysql.getInstance()
if my:
	result = my.query(sql)
	for i in range(len(result)):
		re = result[i] 
		pgs = PGS.getInstance()
		if pgs:
			final = pgs.getResult(os.path.join(path,re['dataKey']))
			merge = dict(final, **re)
			mongo = mongo.getInstance()
			objId = mongo.put(merge)
			print objId