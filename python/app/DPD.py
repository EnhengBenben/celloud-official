#!/usr/bin/python
# -*- coding: utf-8 -*-
# python version 2.7.6

__des__ = 'DPD的操作类'
__author__ = 'lin'

import os
import threading
import sys
reload(sys)
sys.setdefaultencoding('utf8')
from utils.FileUtils import *

class DPD:
	path = None
	instance = None

	locker = threading.Lock()

	#初始化
	def __init__(self):
		pass

	#获取单例对象
	@staticmethod
	def getInstance():
		DPD.locker.acquire()
		try:
			if not DPD.instance:
				DPD.instance = DPD()
			return DPD.instance
		finally:
			DPD.locker.release()

	#执行
	def getResult(self,path,appName,fileName,anotherName):
		result = {}
		if(path.endswith(os.sep)):
			datakey = str(os.path.split(path[:-1])[1])
		else:
			datakey = str(os.path.split(path)[1])
			path = path+os.sep

		if(not os.path.exists(path)):
			return result

		#report.txt
		report = os.path.join(path,'report.txt')
		if (os.path.exists(report)):
			result['report'] = readAllChinese(report)

		#report.txt.wz.1
		wz1 = os.path.join(path,'report.txt.wz.1')
		if (os.path.exists(wz1)):
			info = readAllChinese(wz1)
			result['position'] = info

		#report.txt.wz.2
		wz2 = os.path.join(path,'report.txt.wz.2')
		if (os.path.exists(wz2)):
			result['mutationPosition'] = readAllChinese(wz2)

		#report.txt.Report
		conclusion = os.path.join(path,'report.txt.Report')
		if (os.path.exists(conclusion)):
			result['conclusion'] = readAllChinese(conclusion)

		#SVG
		svgPath=os.path.join(path,'SVG')
		if os.path.exists(svgPath):
			original = [] # 原始峰图
			out = [] # 其它图片
			for x in os.listdir(svgPath):
				if(x == 'Report.txt.seq.txt'):
					result['seq'] = readAll(os.path.join(svgPath,x))
				#以下处理所有png
				elif(x.endswith('_all.png')):
					original.append(x)
				elif(x.endswith('.png')):
					out.append(x)
			result['original'] = sorted(original)
			result['out'] = sorted(out)

		return result
if __name__ == '__main__':
	DPD = DPD.getInstance()
	re = DPD.getResult('/Users/lin/23/106/16032502771259','DPD','a.ab1',None)
	print '1'+re['report']#report.txt
	print '2'+re['position']#report.txt.wz.1
	print '3'+re['conclusion']#report.txt.Report
	print '4'+re['seq']#Report.txt.seq.txt
	print '5'+re['mutationPosition']#report.txt.wz.2
	print '========'
	print re['original']#listAll
	print '========'
	print re['out']#unknow
