#!/usr/bin/python
# -*- coding: utf-8 -*-
# python version 2.7.6

__des__ = 'oncogene的操作类'
__author__ = 'lin'

import os
import threading
import sys
reload(sys)
sys.setdefaultencoding('utf8')
from utils.FileUtils import *

class oncogene:
	path = None
	instance = None

	locker = threading.Lock()

	#初始化
	def __init__(self):
		pass

	#获取单例对象
	@staticmethod
	def getInstance():
		oncogene.locker.acquire()
		try:
			if not oncogene.instance:
				oncogene.instance = oncogene()
			return oncogene.instance
		finally:
			oncogene.locker.release()

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
		#report.txt.wz.1
		wz1 = os.path.join(path,'report.txt.wz.1')
		if (os.path.exists(wz1)):
			result['wz1'] = readAllChinese(wz1)

		#report.txt.wz.2
		wz2 = os.path.join(path,'report.txt.wz.2')
		if (os.path.exists(wz2)):
			result['wz2'] = readAllChinese(wz2)

		#report.txt
		report = os.path.join(path,'report.txt')
		if (os.path.exists(report)):
			rep = readAll(report)
			result['report'] = rep
			if rep == None:
				result['length'] = 0
			else:
				result['length'] = rep[4:].strip()

		#report.txt.wz.Report
		conclusion = os.path.join(path,'report.txt.wz.Report')
		if (os.path.exists(conclusion)):
			result['conclusion'] = readAllChinese(conclusion)

		#pdf
		#createPDF(path,appName,fileName)
		#pdf = os.path.join(path,'oncogene_SNP.pdf')
		#if(os.path.exists(pdf)):
		#	result['pdf'] = 'oncogene_SNP.pdf'

		#SVG
		svgPath = os.path.join(path,'SVG')
		if os.path.exists(svgPath):
			# 所有已知图片
			allKnown = ['1_all.png', '2_all.png', '3_all.png', '4_all.png','5_all.png',
				'ERCC1.118.3.png',
				'EGFR.719.3.png', 'EGFR.768.3.png', 'EGFR.790.3.png', 'EGFR.858.3.png',
				'EGFR.861.3.png' ,'KRAS.12.3.png','KRAS.13.3.png','UGT1A1.71.3.png',
				'ERCC1.118.10.png',
				'EGFR.719.10.png','EGFR.768.10.png','EGFR.790.10.png','EGFR.858.10.png',
				'EGFR.861.10.png', 'KRAS.12.10.png','KRAS.13.10.png','UGT1A1.71.10.png',
				'Indel.30.png',
				"GJB2.24.3.png","GJB2.24.10.png","GJB3.180.3.png","GJB3.180.10.png",
				"GJB3.183.3.png","GJB3.183.10.png","GJB3.256.3.png","GJB3.256.10.png",
				"GJB3.266.3.png","GJB3.266.10.png","SLC26A4.94.3.png","SLC26A4.94.10.png",
				"SLC26A4.197.3.png","SLC26A4.197.10.png","SLC26A4.392.3.png","SLC26A4.392.10.png",
				"SLC26A4.409.3.png","SLC26A4.409.10.png","SLC26A4.410.3.png","SLC26A4.410.10.png",
				"SLC26A4.659.3.png","SLC26A4.659.10.png","SLC26A4.676.3.png","SLC26A4.676.10.png",
				"SLC26A4.721.3.png","SLC26A4.721.10.png","SLC26A4.723.3.png","SLC26A4.723.10.png",
				"OTOF.1341.3.png","OTOF.1341.10.png","OTOF.1607.3.png","OTOF.1607.10.png",
				"SLC17A8.275.3.png","SLC17A8.275.10.png"
				]
			# 已知突变位点
			know_mutation = ['Indel.30.png','ERCC1.118.10.png','EGFR.719.10.png',
				'EGFR.768.10.png','EGFR.790.10.png','EGFR.858.10.png','EGFR.861.10.png',
				'KRAS.12.10.png','KRAS.13.10.png','UGT1A1.71.10.png',
				"GJB2.24.10.png","GJB3.180.10.png","GJB3.183.10.png",
				"GJB3.256.10.png","GJB3.266.10.png","SLC26A4.94.10.png",
				"SLC26A4.197.10.png","SLC26A4.392.10.png","SLC26A4.409.10.png",
				"SLC26A4.410.10.png","SLC26A4.659.10.png","SLC26A4.676.10.png",
				"SLC26A4.721.10.png","SLC26A4.723.10.png","OTOF.1341.10.png",
				"OTOF.1607.10.png","SLC17A8.275.10.png"
				]
			km = []
			original = {} # 原始峰图
			out = [] # 其它图片
			for x in os.listdir(svgPath):
				if(x == 'Report.txt.seq.txt'):
					result['seq'] = readAll(os.path.join(svgPath,x))
				#以下处理所有png
				elif(x.endswith('_all.png')):
					original[x.replace('.','_')] = x
				elif(x in know_mutation):
					km.append(x)
				elif(x not in allKnown and (not x.endswith('.10.png')) and x.endswith('.png')):
					out.append(x)
			result['knowMutation'] = km
			result['original'] = original
			result['out'] = out

		return result
if __name__ == '__main__':
	oncogene = oncogene.getInstance()
	re = oncogene.getResult('/Users/lin/15122400418712','oncogene','a.ab1',None)
	print '1'+re['wz1']
	print '2'+re['wz2']
	print '3'+re['report']
	print '4'+re['length']
	print '5'+re['conclusion']
	print '6'+re['seq']
	print re['original']
	print re['out']
