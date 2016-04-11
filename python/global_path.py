#!/usr/bin/python
# -*- coding: utf-8 -*-
# python version 2.7.6

__des__='全局 path'
__author__='lin'

import os
from mysql.mysqlOperate import mysql

basePath = '/share/data/file/'
sql = 'select file_id,file_name,data_key from tb_file  where path is null'

def dataDeal():
	my=mysql.getInstance()
	if my:
		result = my.query(sql)
		for data in result:
			key = data['data_key']
			name = data['file_name']
			ext = getExt(name)
			if ext != None:
				path = os.path.join(basePath,key + ext)
				updateSQl = 'update tb_file set path="' + path + '" where file_id=' + str(data['file_id'])
				my.execute(updateSQl)
			else:
				print 'error:无法获取后缀'

def getExt(name):
	ext = None
	if name.find('.')>0:
		name = name.lower()
		if name.endswith('.fastq.tar.gz') :
			ext = '.fastq.tar.gz'
		elif name.endswith('.fastq.gz') :
			ext = '.fastq.gz'
		elif name.endswith('.tar.gz'):
			ext = '.tar.gz'
		else:
			ext = name[name.rfind('.'):]
	else:
		print 'error:'+name
	return ext

dataDeal()

if __name__ == '__main__':
	dataDeal()
	print '🐒🐒🐱'
