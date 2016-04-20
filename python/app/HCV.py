#!/usr/bin/python
# -*- coding: utf-8 -*-
# python version 2.7.6

__des__ = 'HCV的操作类'
__author__ = 'lin'

import os
import codecs
import threading
from model import *
from utils.FileUtils import *
from mongo.mongoOperate import mongo
from PDFPro import PDFPro

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
			# 数据参数同比
			# 截取数据报告路径
			paths = path.split(os.sep);
			# 判断该用户是否为测试用户
			if(paths[len(paths) - 3] not in PDFPro.userList):
				# 读取文件最后一行内容
				f = codecs.open(txt,'r','gbk');
				targetLine = '';
				while True:
					line = f.readline();
					if not line:
						break;
					targetLine = line;
				# 分割最后一行数据
				types = targetLine.split('\t');
				if(len(types) > 2):
					# 判断mongodb中是否存在相同的datakey
					#获取mongo操作类实例
					mo = mongo.getInstance();
					if(mo.findAllByCondition({'dataKey':paths[len(paths)-1]},'HCVCount').count() > 0):
						mo.deleteAllByCondition({'dataKey':paths[len(paths)-1]},'HCVCount');
					resultCount = {};
					resultCount['userId'] = int(paths[len(paths) - 4]);
					resultCount['dataKey'] = paths[len(paths) - 2];
					resultCount['subtype'] = types[1];
					mo.insertBatch(resultCount,'HCVCount');
				f.close();
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
	re = hcv.getResult('G:\23\80\20151119393725','HCV','a.ab1',None)
	for i in re:
		print re[i]
