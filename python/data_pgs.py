#!/usr/bin/python
# -*- coding: utf-8 -*-
# python version 2.7.6

__author__='lin'

import os
import sys
from app.PGS import PGS
from mysql.mysqlOperate import mysql
from mongo.mongoOperate import mongo

def pgsdata(path,projectId):
	if(not path.endswith(os.sep)):
		path = path+os.sep
	sql = "select f.*,c.companyId,c.companyName,c.companyEngName,c.companyIcon,c.companyAddr,c.companyEnAddr,c.zipCode,c.companyTel,c.deptName,c.deptEngName,c.deptIcon,c.deptTel  ,s.appId,s.appName,s.createDate,s.projectId,u.username,u.email  from (select file_id as fileId,user_id as userId,data_key as dataKey,file_name as fileName,size,another_name as anotherName,create_date as uploadDate from tb_file where file_id in (select file_id from tb_data_project_relat where project_id = "+projectId+")) as f    left join tb_user u   on f.userId = u.user_id  left join (  select c.company_id as companyId,c.company_name as companyName,c.english_name as companyEngName,c.company_icon as companyIcon,c.address as companyAddr,c.english_name as companyEnAddr,c.zip_code as zipCode,c.tel as companyTel,d.dept_name as deptName,d.english_name as deptEngName,d.dept_icon as deptIcon,d.tel as deptTel,u.user_id as userId from tb_user u,tb_dept d,tb_company c where u.dept_id = d.dept_id and d.company_id = c.company_id) c     on f.userId = c.userId   left join (select r.user_id,r.software_id as appId,s.software_name as appName,r.create_date as createDate,r.project_id as projectId from tb_report r,tb_software s where r.project_id = "+projectId+" and r.software_id = s.software_id) as s   on f.userId = s.user_id;"
	my=mysql.getInstance()
	if my:
		result = my.query(sql)
		if result is not None:
			proReport = "<table><tr><td>dataName</td><td>dataKey</td></tr>";
			for i in range(len(result)):
				re = result[i] 
				pgs = PGS.getInstance()
				if pgs:
					final = pgs.getResult(os.path.join(path,re['dataKey']))
					proReport = proReport + "<tr><td>" + re['fileName'] + "</td><td>" + re['dataKey'] + "</td></tr>"
					merge = dict(final, **re)
					mo = mongo.getInstance()
					objId = mo.put(merge)
					#print objId
			proReport =proReport + "</table>"
			proSql = "update tb_report set state=3,context='" + proReport + "',end_date=now()  where project_id=" + projectId
			print proSql
			my.execute(proSql)
if __name__=='__main__':
    pgsdata("/home/lin/work/9/88","2246")