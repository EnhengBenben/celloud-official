#!/usr/bin/python
# -*- coding: utf-8 -*-
# python version 2.7.6

__des__='文件操作工具类'
__author__='lin'

#统计文件行数
def countLines(path):
	for count,line in enumerate(open(path,'rU')):
		pass
	return count + 1

#读取文件内容
def readAll(path):
	with open(path,'r') as f:
		return f.read().strip()