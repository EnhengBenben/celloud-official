#!/usr/bin/python
# -*- coding: utf-8 -*-
# python version 2.7.6

__des__='全局 MD5'
__author__='lin'

import os
import io
import sys
import hashlib
from mysql.mysqlOperate import mysql

basePath = '/share/data/file/'
sql = 'select path,data_key from tb_file  where md5 is null'

def dataDeal():
	my=mysql.getInstance()
	if my:
		result = my.query(sql)
		for data in result:
			key = data['data_key']
			path = data['path']
			if(os.path.exists(path)):
				md5 = getMD5(path)
				updateSQl = 'update tb_file set md5="' + md5 + '" where data_key="' + key +'"'
				my.execute(updateSQl)
			else:
				print path
				print 'error:文件不存在'

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

def getMD5(path):
	m = hashlib.md5()
	file = io.FileIO(path,'r')
	bytes = file.read(1024)
	while(bytes != b''):
		m.update(bytes)
		bytes = file.read(1024) 
	file.close()
	return m.hexdigest()

dataDeal()

if __name__ == '__main__':
	print '🐒🐒🐱'
	#dataDeal()
	#f = '20150817346217.fastq.gz'
	#f = f[0:f.index('.')]
	#print f
	
	#path = basePath + 'hbc.ab1'
	#print getMD5(path)
	#path = basePath + 'lqx_test1.bam'
	#print getMD5(path)
	#path = basePath + 'pwd.txt'
	#print getMD5(path)
	#path = basePath + 'sql.txt'
	#print getMD5(path)
