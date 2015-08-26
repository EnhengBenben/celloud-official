#!/usr/bin/python
# -*- coding: utf-8 -*-
# python version 2.7.6

__des__='表格操作工具类'
__author__='lin'

from reportlab.platypus import Paragraph

#文件转化成表格数据
def simpleTable(path,titleStyle,contextStyle):
	result = []
	for count,line in enumerate(open(path,'rU')):
		val = line.strip().split("\t")
		if (count == 0):
			re = map(lambda x: Paragraph(x, titleStyle), val)
		else:
			re = map(lambda x: Paragraph(x, contextStyle), val)
		result.append(re)
	return result