#!/usr/bin/python
# -*- coding: utf-8 -*-
# python version 2.7.6

__des__ = 'Translate的操作类'
__author__ = 'lin'

import os,shutil
import codecs
import threading
import sys
reload(sys)
sys.setdefaultencoding('utf8')
from utils.FileUtils import *

class Translate:
	path = None
	instance = None

	locker = threading.Lock()

	#初始化
	def __init__(self):
		pass

	#获取单例对象
	@staticmethod
	def getInstance():
		Translate.locker.acquire()
		try:
			if not Translate.instance:
				Translate.instance = Translate()
			return Translate.instance
		finally:
			Translate.locker.release()

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

		#result.txt
		resultPath = os.path.join(path,'result.txt')
		if(os.path.exists(resultPath)):
			result['result'] = readAll(resultPath)
		return result

if __name__ == '__main__':
	translate = Translate.getInstance()
	re = translate.getResult('/Users/lin/23/73/20150626002788','Translate','a.ab1',None)
	print re['result']
