#!/usr/bin/python
# -*- coding: utf-8 -*-
# python version 2.7.6

__author__='lin'

#达安数据整理方法 eg:python data_arrange.py

import os,shutil,time
from mysql.mysqlOperate import mysql
from utils.FileUtils import *

name = {
11:'ABI_NJ',
80:'HCV',
82:'HBV',
84:'EGFR',
89:'KRAS',
90:'TB-Rifampicin',
105:'TB-INH',
106:'DPD',
107:'BRAF',
108:'UGT'
}

ISOTIMEFORMAT='%Y-%m-%d %X'
print 'start:'+time.strftime(ISOTIMEFORMAT)
endTime = time.strftime(ISOTIMEFORMAT)[0:8]+'00'
toPath = '/share/data1/DAAN/'
ids = '9,12,15,16,18,20,21,23,24,27,28,71'
sql= 'select distinct f.data_key,f.file_name,f.create_date,f.path,r.app_id,c.company_name from tb_report r,tb_file f ,tb_user u,tb_company c,tb_user_company_relat ucr where r.file_id = f.file_id and f.user_id = u.user_id and u.company_id = c.company_id and  u.user_id=ucr.user_id and ucr.company_id =3 and u.user_id not in ('+ids+') and f.create_date<"'+endTime+'"'
print sql
my=mysql.getInstance()
if my:
	result = my.query(sql)
	for d in result:
		timeFolder = str(d['create_date'])[0:7]
		if not os.path.exists(os.path.join(toPath,timeFolder+'.zip')):
			app = name[d['app_id']]
			p = os.path.join(toPath,timeFolder,app,d['company_name'])
			if not os.path.exists(p):
				os.makedirs(p)

			fpath = d['path']
			if os.path.exists(fpath):
				shutil.copy(fpath,p)
			else:
				print '该文件不存在：'+fpath
for f in os.listdir(toPath):
	if(not f.endswith('.zip')):
		tpath = os.path.join(toPath,f)
		zip =  os.path.join(toPath,f+'.zip')
		if not os.path.exists(zip):
			zip_dir(tpath,zip)
			shutil.rmtree(tpath)
print 'end:'+time.strftime(ISOTIMEFORMAT)
