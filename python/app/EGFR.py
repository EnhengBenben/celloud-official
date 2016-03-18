#!/usr/bin/python
# -*- coding: utf-8 -*-
# python version 2.7.6

__des__ = 'EGFR的操作类'
__author__ = 'lin'

import os
import threading
import sys
reload(sys)
sys.setdefaultencoding('utf8')
from utils.FileUtils import *
from EGFR_PDF import *

class EGFR:
	path = None
	instance = None

	locker = threading.Lock()

	#初始化
	def __init__(self):
		pass

	#获取单例对象
	@staticmethod
	def getInstance():
		EGFR.locker.acquire()
		try:
			if not EGFR.instance:
				EGFR.instance = EGFR()
			return EGFR.instance
		finally:
			EGFR.locker.release()

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
			result['pos'] = info.replace('Exon','').strip()

		#report.txt.wz.2
		wz2 = os.path.join(path,'report.txt.wz.2')
		if (os.path.exists(wz2)):
			result['mutationPosition'] = readAllChinese(wz2)

		#report.txt.wz.Report
		conclusion = os.path.join(path,'report.txt.wz.Report')
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
			# 已知突变位点前后10bp图片
			know_mutation_big = ['719.10.png','768.10.png', '790.10.png', '858.10.png', 
			'861.10.png','Indel.30.png']
			# 已知突变位点
			know_mutation = ['719.png','768.png','790.png','858.png','861.png']
			
			kmb = []
			km = []
			original = [] # 原始峰图
			out = [] # 其它图片
			for x in os.listdir(svgPath):
				if(x == 'Report.txt.seq.txt'):
					result['seq'] = readAll(os.path.join(svgPath,x))
				#以下处理所有png
				elif(x.endswith('_all.png')):
					original.append(x)
				elif(x in know_mutation):
					km.append(x)
				elif(x in know_mutation_big):
					kmb.append(x)
				elif(x.endswith('.png') and (not x.endswith('.10.png'))):
					out.append(x)
			result['knowMutationBig'] = sorted(kmb)
			result['knowMutation'] = sorted(km)
			result['original'] = sorted(original)
			result['out'] = sorted(out)

		return result
if __name__ == '__main__':
	allPDF = fileSearch('/Users/lin/23/84/16022602565641','.pdf','endswith')
	if len(allPDF)==1:
		print allPDF[0]
	list = ['5_all.png','1_all.png','2_all.png','3_all.png','4_all.png']
	list.append('7_all.png')
	list.sort()
	print list
	print list.sort()
	print sorted(list)
	EGFR = EGFR.getInstance()
	re = EGFR.getResult('/Users/lin/23/84/16022602565641','EGFR','a.ab1',None)
	print '1'+re['report']
	print '2'+re['position']#wz1
	print '3'+re['pos']#length
	print '4'+re['conclusion']#report
	print '5'+re['seq']#seq
	print '6'+re['pdf']
	print '7'+re['mutationPosition']#wz2
	print '========'
	print re['knowMutationBig']#know
	print '========'
	print re['knowMutation']
	print '========'
	print re['original']#listAll
	print '========'
	print re['out']#unknow
