#!/usr/bin/python
# -*- coding: utf-8 -*-
# python version 2.7.6

__des__ = 'HBV的操作类'
__author__ = 'lin'

import os,shutil
import codecs
import threading
import sys
reload(sys)
sys.setdefaultencoding('utf8')
from utils.FileUtils import *
from HBV_PDF import *
from HBV_html import *
from mongo.mongoOperate import mongo

class HBV:
	path = None
	instance = None

	locker = threading.Lock()

	#初始化
	def __init__(self):
		pass

	#获取单例对象
	@staticmethod
	def getInstance():
		HBV.locker.acquire()
		try:
			if not HBV.instance:
				HBV.instance = HBV()
			return HBV.instance
		finally:
			HBV.locker.release()

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

		#clinical.txt
		clinicalpath = os.path.join(path,'clinical.txt')
		if (os.path.exists(clinicalpath)):
			sb = "<table class='table table-bordered table-condensed'>"
			i = 0	# 记录上一个*所在的行
			i_num = 0	# 第几个*
			isFirst = True	# 是否*后第一个tr
			rowspan = {}	#存储每个*需要合并的行
			j = 0	#记录当前行
			pos = 0	#记录位点
			site = {}
			clinical = codecs.open(os.path.join(path,'clinical.txt'),'r','gbk')
			for (j,line) in enumerate(clinical):
				l = line.strip()
				if(l.startswith('*')):
					rowspan['_'+str(i_num)] = (j-i-1)/3*2
					i_num = i_num + 1
					i = j
					isFirst = True
					sb += "<tr><td rowspan='_" + str(i_num) + "' class='snpLeft'>"+l.replace("*", "")+"</td>"
				else:
					remainder = (j-i_num)%3
					if(remainder==0):
						if(isFirst):
							isFirst = False
						else:
							sb += "<tr>"
						if(l.startswith('3')):
							l = l[1:]
							sb+="<td rowspan=2 class='_red'>"
						else:
							sb+="<td rowspan=2>"
						sb += l + "</td>"
						pos = l[1:4]
					if(remainder==2):
						sb+="<tr>"
					if(remainder==1 or remainder==2):
						if(l.startswith('1')):
							l = l[1:]
							sb+="<td class='_hard'>"
						elif(l.startswith('2')):
							l = l[1:]
							sb+="<td class='_light'>"
						elif(l.startswith('3')):
							l = l[1:]
							sb+="<td class='_red'>"
						else:
							sb+="<td>"
						sb+=l+"</td></tr>"
						if(remainder==1):
							l = l.replace('野生型: ','')
							p = str(pos) + '_wild'
						elif(remainder==2):
							l = l.replace('突变型: ','')
							p = str(pos) + '_mutation'
						site[p] = l
			clinical.close()
			rowspan['_'+str(i_num)] = (j-i)/3*2
			sb+="</table>"
			for (d,x) in rowspan.items():
				sb = sb.replace(d,str(x))
			result['clinical'] = sb
			result['site'] = site
		#low.qc
		lowQc = os.path.join(path,'low.qc')
		if(os.path.exists(lowQc)):
			lowQcFile = open(lowQc,'r')
			lowQcArray = []
			for line in lowQcFile.readlines():
				lowQcArray.append(line)
			# 去重
			result['lowQc'] = set(lowQcArray)
		#pdf
		createPDF(path,appName,fileName)
		pdf = os.path.join(path,'HBV_SNP.pdf')
		if(os.path.exists(pdf)):
			result['pdf'] = 'HBV_SNP.pdf'
		#SVG
		svgPath = os.path.join(path,'SVG')
		if (os.path.exists(svgPath)):
			know = ['169.png','173.png','180.png','181.png','184.png','194.png','202.png','204.png','236.png','250.png','169.10.png','173.10.png','180.10.png','181.10.png','184.10.png','194.10.png','202.10.png','204.10.png','236.10.png','250.10.png']
			known = {} # 已知位点峰图
			original = {} # 原始峰图
			other = {} # 其他位点峰图
			out = {} # 除了以上三种外的其它图片
			for x in os.listdir(svgPath):
				if(x == 'Report.txt.seq.txt'):
					seq = readAll(os.path.join(svgPath,x))
					result['seq'] = seq
				elif(x == 'type.txt'):
					typetxt = readAll(os.path.join(svgPath,x))
					result['type'] = typetxt
				elif(x == 'Report.txt.204'):
					txt204 = readAllChinese(os.path.join(svgPath,x))
					result['txt204'] = txt204
				elif(x == 'Report.txt'):
					reporttxt = readAllChinese(os.path.join(svgPath,x))
					result['reporttxt'] = reporttxt
				#以下处理所有png
				elif(x.endswith('_all.png')):
					original[x.replace('.','_')] = x
				elif(x.endswith('_new.png')):
					other[x.replace('.','_')] = x
				elif(x in know):
					known[x.replace('.','_')] = x
				elif(x.endswith('png')):
					out[x.replace('.','_')] = x
			result['known'] = known
			result['original'] = original
			result['other'] = other
			result['out'] = out
			if 'type' not in result.keys() or result['type']=='':
				result['reporttxt']='测序失败，建议重测'

		#zip
		zip = os.path.join(path,'HBV_SNP.zip')
		if(os.path.exists(zip)):
			os.remove(zip)
		zipFolder = os.path.join(path,'HBV_SNP')
		if(os.path.exists(zipFolder)):
			shutil.rmtree(zipFolder)
		createHTML(path,fileName,result['type'],result['other'],result['reporttxt'])
		if(os.path.exists(zip)):
			result['zip'] = 'HBV_SNP.zip'
		return result
if __name__ == '__main__':
	hbv = HBV.getInstance()
	re = hbv.getResult('/Users/lin/23/82/1','HBV','a.ab1',None)
	#mo = mongo.getInstance()
	#objId = mo.put(re,'HBV')
	#createHTML('/Users/lin/23/82/1','aaaa.ab1',re['type'],re['other'],re['reporttxt'])