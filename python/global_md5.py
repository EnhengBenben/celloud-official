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
sql = 'select file_name,data_key from tb_file'

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
				if(os.path.exists(path)):
					md5 = getMD5(path)
					updateSQl = 'update tb_file set md5="' + md5 + '" where data_key=' + key
					print updateSQl
					my.execute(updateSQl)
				else:
					print path
					print 'error:文件不存在'
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

def getMD5(path):
	m = hashlib.md5()
	file = io.FileIO(path,'r')
	bytes = file.read(1024)
	while(bytes != b''):
		m.update(bytes)
		bytes = file.read(1024) 
	file.close()
	return m.hexdigest()

if __name__ == '__main__':
	print '🐒🐒🐱'
	dataDeal()
	#print getExt('中检所报价清单_v1.0.0.xlsx')
	#print getExt('FileZilla_3.6.0.2_win32-setup.exe')
	#print getExt('index11.lis')
	#print getExt('xxxxx.txt')

	#print getExt('3_muF2.ab1')

	#print getExt('test.fq')
	#print getExt('13135A_L6-1_L2_I010.R1.clean.fastq')
	#print getExt('13765B_C03_GTCCGC_L002_R1_001.fastq.gz')
	#print getExt('fastq.fastq.fastq.tar.gz')

	#print getExt('20141206983436.fasta')
	#print getExt('20141206983436.fa')

	#print getExt('IonXpress_011_S106WF1.bam')
	#print getExt('IonXpress_001_rawlib.basecaller.bam')

	
	#path = basePath + 'hbc.ab1'
	#print getMD5(path)
	#path = basePath + 'lqx_test1.bam'
	#print getMD5(path)
	#path = basePath + 'pwd.txt'
	#print getMD5(path)
	#path = basePath + 'sql.txt'
	#print getMD5(path)
