#!/usr/bin/python
# -*- coding: utf-8 -*-
# python version 2.7.6

__des__ = 'KRAS的操作类'
__author__ = 'lin'

import os
import threading
import sys
reload(sys)
sys.setdefaultencoding('utf8')
from utils.FileUtils import *
from KRAS_PDF import *

class KRAS:
	path = None
	instance = None

	locker = threading.Lock()

	#初始化
	def __init__(self):
		pass

	#获取单例对象
	@staticmethod
	def getInstance():
		KRAS.locker.acquire()
		try:
			if not KRAS.instance:
				KRAS.instance = KRAS()
			return KRAS.instance
		finally:
			KRAS.locker.release()

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
			context = readAllChinese(report)
			result['report'] = context
			result['pos'] = context.replace('KRAS exon number is ','').strip()

		#report.txt.wz.1
		wz1 = os.path.join(path,'report.txt.wz.1')
		if (os.path.exists(wz1)):
			result['position'] = readAllChinese(wz1)

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
				elif(x.endswith('.png')):
					out.append(x)
			result['original'] = sorted(original)
			result['out'] = sorted(out)

		return result

if __name__ == '__main__':
	KRAS = KRAS.getInstance()
	re = KRAS.getResult('/Users/lin/23/89/20150515173271','KRAS','a.ab1',None)
	print '1'+re['report']
	print '1.5'+re['pos']
	print '2'+re['position']#wz1
	print '3'+re['mutationPosition']#wz2
	print '4'+re['conclusion']#report
	print '5'+re['seq']#seq
	print '6'+re['pdf']
	print '========'
	print re['original']#listAll
	print '========'
	print re['out']#unknow
