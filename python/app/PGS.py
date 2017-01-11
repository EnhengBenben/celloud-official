#!/usr/bin/python
# -*- coding: utf-8 -*-
# python version 2.7.6

__des__ = 'PGS的操作类'
__author__ = 'lin'

import os
import threading
from utils.FileUtils import *
from PGS_PDF import *
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
	def getResult(self,path,appName,fileName,anotherName):
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
			createPDF(path,appName,fileName,anotherName)
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
							v = ''
							if ( i > len(val)-1 ):
								v = '.'
							else:
								v = val[i]
							if(v.strip()=='\'\''):
								v = '.'
							result[pgs[k]] = v
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
				elif(x == 'report.mosaic.txt'):
					mosaic = readAll(os.path.join(path,"report.mosaic.txt"))
					result['mosaic'] = mosaic
				elif(x.endswith('.HR.split.png')):
					result['HRSplitPng'] = x
				elif(x.endswith('.HR.report.txt.split.png')):
					result['HRReportPng'] = x
				elif(x.endswith('.png') and ('report.txt' in x)):
					result['report' + x.split('.')[-2].capitalize() + 'Png'] = x
				elif(x.endswith('.png') and ('final.txt.test1' in x)):
					result['finalTest1Png'] = x
				elif(x.endswith('.png') and ('report.txt.test1' in x)):
					result['reportTest1Png'] = x
				elif(x.endswith('.png') and ('chr-point' in x)):
					##此处处理VgDNA_1M下的图片
					charKeys = x.split('.')[-2];
					result[charKeys.split('-')[0] + charKeys.split('-')[1] + 'Png'] = x
				elif(x.endswith('.png')):
					##此处处理所有png
					result[pgs[x.split('.')[-2] + 'Png']] = x
				elif(x.endswith('.pdf')):
					result['pdf'] = x
			result[pgs['no_enough_reads']] = no_enough_reads
		return result
if __name__ == '__main__':
	pgs = PGS.getInstance()
	re = pgs.getResult('G:\\16091800137373','PGS','a.ab1',None)
	print re
	#mo = mongo.getInstance()
	#objId = mo.put(re,'HBV')
	#createHTML('/Users/lin/23/82/1','aaaa.ab1',re['type'],re['other'],re['reporttxt'])
