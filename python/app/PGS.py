#!/usr/bin/python
# -*- coding: utf-8 -*-
# python version 2.7.6

__des__ = 'PGS的操作类'
__author__ = 'lin'

import os
import threading
from utils.FileUtils import *
from model import *

class PGS:
	path = None
	instance = None

	locker = threading.Lock()

	#初始化
	def __init__(self):
		pass

	#获取单例对象
	@staticmethod
	def getInstance():
		PGS.locker.acquire()
		try:
			if not PGS.instance:
				PGS.instance = PGS()
			return PGS.instance
		finally:
			PGS.locker.release()

	#执行
	def getResult(self,path):
		result = {}
		if(path.endswith(os.sep)):
			datakey = str(os.path.split(path[:-1])[1])
		else:
			datakey = str(os.path.split(path)[1])
			path = path+os.sep
		result['datakey'] = datakey
		##此处先判断是否no_enouth_reads
		noxlspath = os.path.join(path,'no_enough_reads.xls')
		notxtpath = os.path.join(path,'no_enough_reads.txt')
		noxls = os.path.exists(noxlspath)
		notxt = os.path.exists(notxtpath)
		if(notxt or noxls):
			if(notxt):
				no_enough_reads = readAll(notxtpath)
			else:
				no_enough_reads = readAll(noxlspath)
		else:
			no_enough_reads = False
			if(not os.path.exists(path)):
				print  path + ' is not exists'
				return result
			for x in os.listdir(path):
				if(x == 'report.txt'):
					report = readAll(os.path.join(path,'report.txt'))
					result['report'] = report
				elif(x == datakey+".xls"):
					datakeyxls = os.path.join(path,datakey+".xls")
					with open(datakeyxls, 'r') as f:
						fileName = f.readline().strip()
						key = f.readline().strip().split("\t")
						val = f.readline().strip().split("\t")
						for i,k in enumerate(key):
							result[pgs[k]] = val[i]
						lines = countLines(datakeyxls)
						#此处判断文件行数决定是否需要读取note
						if(lines == 4):
							note = f.readline().strip()
							result['note'] = note
						result['fileName'] = fileName
				elif(x == 'report.xls'):
					detail = []
					f = open(os.path.join(path,"report.xls"),'r')
					for line in f.readlines():
						detail.append(line.strip().split("\t"))
					f.close()
					result['detail'] = detail
				elif(x.endswith('.png')):
					##此处处理所有png
					result[pgs[x.split('.')[-2] + 'Png']] = x
				elif(x.endswith('.pdf')):
					result['pdf'] = x
			result[pgs['no_enough_reads']] = no_enough_reads
		return result
