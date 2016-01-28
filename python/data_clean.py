#!/usr/bin/python
# -*- coding: utf-8 -*-
# python version 2.7.6

__author__='lin'

import os
from mysql.mysqlOperate import mysql

basePath = '/share/data/file/'
#清理测试帐号及其文件
ids = '0,6,9,12,15,16,18,21,23,24,27,28,71'
querySQL= "select path from tb_file where user_id in ("+ids+");"
deleteSQL= "delete from tb_file where user_id in ("+ids+");"
my=mysql.getInstance()
if my:
	result = my.query(querySQL)
	for i in result:
		path = i['path']
		if(path != None and os.path.exists(path)):
			os.remove(path)

	my.execute(deleteSQL)

	#清理无主的文件
	for f in os.listdir(basePath):
		if '.' in f:
			datakey = f[0:f.index('.')]
		else:
			datakey = f
		fSQL= "select file_id from tb_file where data_key ='"+datakey+"';"
		result = my.query(fSQL)
		if len(result)==0:
			os.remove(os.path.join(basePath,f))
		