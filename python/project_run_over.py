#!/usr/bin/python
# -*- coding: utf-8 -*-
# python version 2.7.6

__author__='lin'

import os
import sys
from mysql.mysqlOperate import mysql
from mongo.mongoOperate import mongo
from app.ABINJ import ABINJ
from utils.FileUtils import *

#command: python *.py projectId
#eg : python /share/biosoft/perl/PGS_MG/python/project_run_over.py 4018

#对应class名
method_dic = {
11:ABINJ
}

#对应database名
collection_dic = {
11:"ABINJ"
}

path = '/share/data/webapps/Tools/upload'

if len(sys.argv) != 2:
	print 'Usage: *.py projectId'
	sys.exit(0)
projectId = sys.argv[1]

sql = "select f.*,c.companyId,c.companyName,c.companyEngName,c.companyIcon,c.companyAddr,c.companyEnAddr,c.zipCode,c.companyTel,c.deptName,c.deptEngName,c.deptIcon,c.deptTel  ,s.appId,s.appName,s.createDate,s.projectId,s.title,u.username,u.email  from (select file_id as fileId,user_id as userId,data_key as dataKey,file_name as fileName,size,another_name as anotherName,create_date as uploadDate from tb_file where file_id in (select file_id from tb_file_project_relat where project_id = "+projectId+")) as f    left join tb_user u   on f.userId = u.user_id  left join (  select c.company_id as companyId,c.company_name as companyName,c.english_name as companyEngName,c.company_icon as companyIcon,c.address as companyAddr,c.english_name as companyEnAddr,c.zip_code as zipCode,c.tel as companyTel,d.dept_name as deptName,d.english_name as deptEngName,d.dept_icon as deptIcon,d.tel as deptTel,u.user_id as userId from tb_user u,tb_dept d,tb_company c where u.dept_id = d.dept_id and d.company_id = c.company_id) c     on f.userId = c.userId   left join (select r.user_id,r.app_id as appId,s.app_name as appName,s.title,r.create_date as createDate,r.project_id as projectId from tb_report r,tb_app s where r.project_id = "+projectId+" and r.app_id = s.app_id and r.flag=1) as s on f.userId = s.user_id"
my=mysql.getInstance()
if my:
	result = my.query(sql)
	if result is not None:
		re = result[0]
		projectContext = re['title']
		p = os.path.join(path,str(re['userId']),str(re['appId']),str(re['projectId']))
		for i in range(len(result)):
			re = result[i]
			myfun = method_dic[int(re['appId'])]
			fun = myfun.getInstance()
			if fun:
				final = fun.getResult(p)
				merge = dict(final, **re)
				mo = mongo.getInstance()
				objId = mo.put(merge,collection_dic[int(re['appId'])])
				print objId
			projectContext += re['dataKey']+'\t'+re['fileName']+'\n'

		projectTxt = os.path.join(p,str(re['projectId'])+'.txt')
		if not os.path.exists(projectTxt):
			appendWrite(projectTxt,projectContext)
			table = simpleToTable(projectTxt,False)
			updateSql = 'update tb_report set context = "'+table+'",period = 3,end_date=now() where flag = 1 and project_id = '+str(re['projectId'])
			my=mysql.getInstance()
			if my:
				my.execute(updateSql)
			updateSql = 'update tb_report set period = 3,end_date=now() where flag = 0 and project_id = '+str(re['projectId'])
			my=mysql.getInstance()
			if my:
				my.execute(updateSql)
			updateSql = 'update tb_task set period = 2 where project_id = '+str(re['projectId'])
			my=mysql.getInstance()
			if my:
				my.execute(updateSql)
