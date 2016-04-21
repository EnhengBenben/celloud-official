#!/usr/bin/python
# -*- coding: utf-8 -*-
# python version 2.7.6

__des__ = 'EGFR的操作类'
__author__ = 'lin'

import os
import codecs
import threading
import sys
reload(sys)
sys.setdefaultencoding('utf8')
from utils.FileUtils import *
from EGFR_PDF import *
from mongo.mongoOperate import mongo
from PDFPro import PDFPro

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
			#截取数据报告路径
			paths = path.split(os.sep);
			#首先判断用户id是否为测试用户
			if(paths[len(paths) - 4] not in PDFPro.userList):
				#判断mongo中是否有对应dataKey的数据, 如果有则都删除
				#获取mongo操作类实例
				mo = mongo.getInstance();
				if(mo.findAllByCondition({'dataKey':paths[len(paths)-2]},'EGFRCount').count() > 0):
					mo.deleteAllByCondition({'dataKey':paths[len(paths)-2]},'EGFRCount');
				#打开报告
				f = codecs.open(report,'r','gbk');
				#位点的dict
				resultCount = {};
				#读取第一行将\n替换,并将空格替换为\t方便统一操作
				firstLine = f.readline().replace('\n','').replace(' ','\t').strip();
				if((firstLine.startswith('Exon') and len(firstLine.split('\t')) == 2) or (firstLine.startswith('EGFR') and len(firstLine.split('\t')) == 5)):
					#使用\t分割
					firstLines = firstLine.split('\t');
					#截取userId
					resultCount['userId'] = int(paths[len(paths)-4]);
					#截取dataKey
					resultCount['dataKey'] = paths[len(paths)-2];
					#截取length
					resultCount['length'] = int(firstLines[len(firstLines) - 1]);
					result['pos'] = int(firstLines[len(firstLines) - 1]);
					list = [];
					#循环读取剩余的行
					while True:
						line = f.readline().strip();
						if line and line.startswith('SNP'):
							resultCount['site'] = 0;
							lines = line.split('\t');
							target = lines[len(lines) - 2]
							try:
								l = int(target.index('-'));
								before = target[l - 2:l - 1].strip();
								after = target[l + 2:l + 3].strip();
								if(before == after):
									try:
										d = int(target.index(','));
										k = int(target.index(')'));
										resultSize = float(target[d + 1:k]);
										if(resultSize < 5):
											resultCount['site'] = int(lines[1]);
									except ValueError:
										resultCount['site'] = int(lines[1]);
								else:
									resultCount['site'] = int(lines[1]);
							except ValueError:
								resultCount['site'] = int(lines[1]);
							if('site' in resultCount.keys() and resultCount['site'] != 0):
								list.append(resultCount.copy());
						elif line and line.startswith('Indel'):
							lines = line.split('\t');
							resultCount['site'] = int(lines[3]);
							list.append(resultCount.copy());
						else:
							break;
					# 执行批量插入操作
					if(len(list) > 0):
						mo.insertBatch(list,'EGFRCount');
				f.close();

		#report.txt.wz.1
		wz1 = os.path.join(path,'report.txt.wz.1')
		if (os.path.exists(wz1)):
			info = readAllChinese(wz1)
			result['position'] = info

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
