#!/usr/bin/python
# -*- coding: utf-8 -*-
# python version 2.7.6

__des__ = 'HBV的操作类'
__author__ = 'lin'

import os
import codecs
import threading
import sys
reload(sys)
sys.setdefaultencoding('utf8')
from utils.FileUtils import *

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
		result['datakey'] = datakey

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
		#pdf
		pdf = os.path.join(path,'HBV.pdf')
		if(os.path.exists(pdf)):
			result['pdf'] = 'HBV.pdf'
		#zip
		zip = os.path.join(path,'HBV_SNP.zip')
		if(os.path.exists(zip)):
			result['zip'] = zip
		#SVG
		svgPath = os.path.join(path,'SVG')
		if (os.path.exists(svgPath)):
			png = {}
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
				elif(x.endswith('.png')):
					##此处处理所有png
					png[x.replace('.','_')] = x
					
			result['png'] = png
		return result
if __name__ == '__main__':
	hbv = HBV.getInstance()
	re = hbv.getResult('/home/lin/work/9/82/CelLoud92B15GJL','HBV','a.ab1','test')
	print re['site']
	print re['png']
	print re['pdf']
	print re['clinical']
	print re['seq']
	print re['type']
	print re['txt204']
	print re['reporttxt']
	print re['zip']