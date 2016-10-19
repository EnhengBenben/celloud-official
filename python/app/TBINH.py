#!/usr/bin/python
# -*- coding: utf-8 -*-
# python version 2.7.6

__des__ = 'TBINH的操作类'
__author__ = 'mq'

import os
import threading
import sys
reload(sys)
sys.setdefaultencoding('utf8')
from utils.FileUtils import *

class TBINH:
	path = None
	instance = None

	locker = threading.Lock()

	#初始化
	def __init__(self):
		pass

	#获取单例对象
	@staticmethod
	def getInstance():
		TBINH.locker.acquire()
		try:
			if not TBINH.instance:
				TBINH.instance = TBINH()
			return TBINH.instance
		finally:
			TBINH.locker.release()

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
			# isWild: 0代表既不是野生也不是非野生即报告内容为空 1代表是野生 2代表非野生
			info = readAllChinese(report)
			if (info.strip() == ''):
				result['report'] = 'no result'
				result['isWild'] = 0
			else:
				result['report'] = info
				infoSplit = info.split('\n')
				# 第一行
				result['geneName'] = infoSplit[0].strip()
				# 第一行的最后一个单词
				result['simpleGeneName'] = infoSplit[0].split(' ')[len(infoSplit[0].split(' '))-1].strip()
				# 第二行以SNP开头的是非野生
				if(infoSplit[1].startswith('SNP')):
					result['isWild'] = 2
				else:
					result['isWild'] = 1
				

		#report.txt.wz.1
		wz1 = os.path.join(path,'report.txt.1')
		if (os.path.exists(wz1)):
			info = readAllChinese(wz1)
			result['position'] = info

		#report.txt.wz.2
		wz2 = os.path.join(path,'report.txt.2')
		if (os.path.exists(wz2)):
			result['mutationPosition'] = readAllChinese(wz2)

		#report.txt.Report->结论
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
			result['original'] = sorted(original)

		return result
if __name__ == '__main__':
	TBINH = TBINH.getInstance()
	re = TBINH.getResult('/Users/lin/23/105/15121702128377','TBINH','a.ab1',None)
	print '1'+re['report']
	print '2'+re['geneName']
	print '3'+re['position']#report.txt.1
	print '4'+re['conclusion']#report.txt.Report
	print '5'+re['seq']#seq
	print '7'+re['mutationPosition']#wz2
	print '========'
	print re['original']#listAll
