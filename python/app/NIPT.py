#!/usr/bin/python
# -*- coding: utf-8 -*-
# python version 2.7.6

__des__='NIPT的操作类'
__author__='lin'

import os
import threading
from utils.FileUtils import *
from NIPT_PDF import *
from model import *

class NIPT:
	path = None
	instance = None

	locker = threading.Lock()

	#初始化
	def __init__(self):
		pass

	#获取单例对象
	@staticmethod
	def getInstance():
		NIPT.locker.acquire()
		try:
			if not NIPT.instance:
				NIPT.instance = NIPT()
			return NIPT.instance
		finally:
			NIPT.locker.release()

	#执行
	def getResult(self,path):
		result = {}
		if(not path.endswith(os.sep)):
			path = path+os.sep
		datakey = str(os.path.split(path[:-1])[1])
		result['datakey'] = datakey
		if(not os.path.exists(path)):
			print  path + ' is not exists'
			return result
		createPDF(path)
		print  path
		for x in os.listdir(path):
			if(x == 'Data_report.txt'):
				datareport = os.path.join(path,'Data_report.txt')
				with open(datareport, 'r') as f:
					key = f.readline().strip().split()
					val = f.readline().strip().split()
					for i,k in enumerate(key):
						if( i == 2):
							k = 'readsPercent'
						if(i == 4):
							k = 'mappingPercent'
						result[nipt[k]] = val[i]
			elif(x == 'Result'):
				resultFolder = os.path.join(path,"Result")
				for y in os.listdir(resultFolder):
					if( y == 'Zscore.txt'):
						detail = []
						f = open(os.path.join(resultFolder,"Zscore.txt"),'r')
						for line in f.readlines():
							detail.append(line.strip().split())
						f.close()
						result['detail'] = detail
					elif(y == 'Pic.txt.mini.png'):
						result[y.split('.')[-2] + y.split('.')[-1]] = y
			elif(x.endswith('.pdf')):
				result['pdf'] = x
		return result