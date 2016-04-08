#!/usr/bin/python
# -*- coding: utf-8 -*-
# python version 2.7.6

__des__ = 'TBRifampicin的操作类'
__author__ = 'mq'

import os
import threading
import sys
reload(sys)
sys.setdefaultencoding('utf8')
from utils.FileUtils import *
from TBRifampicin_PDF import *

class TBRifampicin:
	path = None
	instance = None

	locker = threading.Lock()

	#初始化
	def __init__(self):
		pass

	#获取单例对象
	@staticmethod
	def getInstance():
		TBRifampicin.locker.acquire()
		try:
			if not TBRifampicin.instance:
				TBRifampicin.instance = TBRifampicin()
			return TBRifampicin.instance
		finally:
			TBRifampicin.locker.release()

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

		#pdf
		allPDF = fileSearch(path,'.pdf','endswith')
		length = len(allPDF)
		if length==1:
			result['pdf'] = allPDF[0]
		elif length>1:
			print '多余的PDF'+path
		else:
			createPDF(path,appName,fileName)
			pdf = os.path.join(path,appName+'.pdf')
			if(os.path.exists(pdf)):
				result['pdf'] = appName+'.pdf'

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
			result['original'] = sorted(original)
		return result

if __name__ == '__main__':
	allPDF = fileSearch('/Users/lin/23/90/16032502767140','.pdf','endswith')
	if len(allPDF)==1:
		print allPDF[0]
	TBRifampicin = TBRifampicin.getInstance()
	re = TBRifampicin.getResult('/Users/lin/23/90/16032502767140','TBRifampicin','a.ab1',None)
	print '1'+re['report'] #report.txt
	print '2'+re['position']#wz1
	print '3'+re['conclusion']#report
	print '4'+re['seq']#seq
	print '5'+re['pdf']
	print '6'+re['mutationPosition']#wz2
	print '========'
	print re['original']#listAll
