#!/usr/bin/python
# -*- coding: utf-8 -*-
# python version 2.7.6

__des__='PDF 表格操作工具类'
__author__='lin'

from reportlab.platypus import Paragraph

#文件转化成 pdf 表格数据
def simpleTable(path,titleStyle,contextStyle):
	result = []
	for count,line in enumerate(open(path,'rU')):
		val = line.strip().split("\t")
		if (count == 0):
			re = map(lambda x: Paragraph('<font size=8>' + x + '</font>', titleStyle), val)
		else:
			re = map(lambda x: Paragraph('<font size=8>' + x + '</font>', contextStyle), val)
		result.append(re)
	return result

#文件指定行转化成 pdf 表格数据
def lineTable(path,style,lines):
	result = []
	for count,line in enumerate(open(path,'rU')):
		if (count in lines):
			val = line.strip().split("\t")
			re = map(lambda x: Paragraph('<font size=8>' + x + '</font>', style), val)
			result.append(re)
	return result