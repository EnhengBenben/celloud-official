#!/usr/bin/python
# -*- coding: utf-8 -*-
# python version 2.7.6

__des__ = 'HCV的操作类'
__author__ = 'lin'

import os
import threading
from model import *
from utils.FileUtils import *
from mongo.mongoOperate import mongo

class HCV:
	path = None
	instance = None

	locker = threading.Lock()

	#初始化
	def __init__(self):
		pass

	#获取单例对象
	@staticmethod
	def getInstance():
		HCV.locker.acquire()
		try:
			if not HCV.instance:
				HCV.instance = HCV()
			return HCV.instance
		finally:
			HCV.locker.release()

	#执行
	def getResult(self,path,appName,fileName,anotherName):
		result = {}
		if(not os.path.exists(path)):
			return result
		#截取datakey
		if(path.endswith(os.sep)):
			datakey = str(os.path.split(path[:-1])[1])
		else:
			datakey = str(os.path.split(path)[1])
			path = path+os.sep

		#Result.txt
		txt = os.path.join(path,'Result.txt')
		if(os.path.exists(txt)):
			context = readAll(txt).split('\n')
			titles = context[0].split('\t')
			values = context[1].split('\t')
			for i in range(len(titles)):
				if i < len(values):
					result[hcvmodel[titles[i]]] = values[i]
				else:
					result[hcvmodel[titles[i]]] = ''
		#seq
		fasta = fileSearch(os.path.join(path,'Fasta'),'.ab1','endswith')
		if(len(fasta)>0):
			seq = readAll(os.path.join(path,'Fasta',fasta[0]))
			result['seq'] = seq
		#SVG，以下处理所有png
		svgPath = os.path.join(path,'SVG')
		if (os.path.exists(svgPath)):
			original = {} # 原始峰图
			for x in os.listdir(svgPath):
				if(x.endswith('_all.png')):
					original[x.replace('.','_')] = x
			result['original'] = original
		return result
if __name__ == '__main__':
	hcv = HCV.getInstance()
	re = hcv.getResult('/Users/lin/Documents/apache-tomcat-7.0.65/webapps/Tools/upload/23/80/20151109502832','HCV','a.ab1',None)
	for i in re:
		print re[i]
