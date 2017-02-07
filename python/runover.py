#!/usr/bin/python
# -*- coding: utf-8 -*-
# python version 2.7.6

__author__='lin'

import os
import sys
from mysql.mysqlOperate import mysql
from mongo.mongoOperate import mongo
from app.PGS import PGS
from app.NIPT import NIPT
from app.HBV import HBV
from app.EGFR import EGFR
from app.HCV import HCV
from app.oncogene import oncogene
from app.KRAS import KRAS
from app.DPD import DPD
from app.UGT import UGT
from app.BRAF import BRAF
from app.TBRifampicin import TBRifampicin
from app.TBINH import TBINH
from app.Translate import Translate
from app.S16 import S16

#command: python *.py basePath projectId
#eg : python runover.py '/share/data/webapps/Tools/upload/' 4018
#eg : python /home/lin/work/git/celloud/python/runover.py /home/lin/work/ 4018

#对应class名
method_dic = {
1:S16,
73:Translate,
80:HCV,
82:HBV,
84:EGFR,
85:PGS,
86:PGS,
87:PGS,
88:PGS,
89:KRAS,
90:TBRifampicin,
91:PGS,
92:PGS,
93:PGS,
94:PGS,
104:PGS,
105:TBINH,
116:PGS,
95:NIPT,
106:DPD,
107:BRAF,
108:UGT,
117:oncogene,
119:PGS,
120:PGS,
121:PGS,
122:PGS,
124:PGS,
125:PGS,
129:PGS,
130:PGS,
144:HBV
}

#对应database名
collection_dic = {
1:"S16",
73:"Translate",
80:"HCV",
82:"HBV",
84:"EGFR",
85:"Pgs",
86:"Pgs",
87:"Pgs",
88:"Pgs",
89:"KRAS",
90:"TBRifampicin",
91:"Pgs",
92:"Pgs",
93:"Pgs",
94:"Pgs",
104:"Pgs",
105:"TBINH",
116:"Pgs",
95:"NIPT",
106:"DPD",
107:"BRAF",
108:"UGT",
117:"Oncogene",
119:"Pgs",
120:"Pgs",
121:"Pgs",
122:"Pgs",
124:"Pgs",
125:"Pgs",
129:"Pgs",
130:"Pgs",
144:"HBV"
}

if len(sys.argv) != 4:
	print 'Usage: *.py path projectId'
	sys.exit(0)
path = sys.argv[1]
projectId = sys.argv[2]
dataKey = sys.argv[3]

sql = "select f.*,c.companyId,c.companyName,c.companyEngName,c.companyIcon,c.companyAddr,c.companyEnAddr,c.zipCode,c.companyTel,c.deptName,c.deptEngName,c.deptIcon,c.deptTel  ,s.appId,s.appName,s.createDate,s.projectId,u.username,u.email  from (select file_id as fileId,user_id as userId,data_key as dataKey,file_name as fileName,size,another_name as anotherName,create_date as uploadDate from tb_file where file_id in (select file_id from tb_file_project_relat where project_id = "+projectId+")) as f    left join tb_user u   on f.userId = u.user_id  left join (  select c.company_id as companyId,c.company_name as companyName,c.english_name as companyEngName,c.company_icon as companyIcon,c.address as companyAddr,c.english_name as companyEnAddr,c.zip_code as zipCode,c.tel as companyTel,d.dept_name as deptName,d.english_name as deptEngName,d.dept_icon as deptIcon,d.tel as deptTel,u.user_id as userId from tb_user u,tb_dept d,tb_company c where u.dept_id = d.dept_id and d.company_id = c.company_id) c     on f.userId = c.userId   left join (select r.user_id,r.app_id as appId,s.app_name as appName,r.create_date as createDate,r.project_id as projectId from tb_report r,tb_app s where r.project_id = "+projectId+" and r.app_id = s.app_id and r.flag=1) as s on f.userId = s.user_id"
my=mysql.getInstance()
if my:
	result = my.query(sql)
	if result is not None:
		for i in range(len(result)):
			re = result[i]
			if str(re['dataKey'])==dataKey:
				myfun = method_dic[int(re['appId'])]
				fun = myfun.getInstance()
				if fun:
					p = os.path.join(path,str(re['userId']),str(re['appId']),str(re['dataKey']))
					final = fun.getResult(p,re['appName'],re['fileName'],re['anotherName'])
					merge = dict(final, **re)
					mo = mongo.getInstance()
					objId = mo.put(merge,collection_dic[int(re['appId'])])
					print objId
				break
				