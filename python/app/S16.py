#!/usr/bin/python
# -*- coding: utf-8 -*-
# python version 2.7.6

__des__ = '16s的操作类'
__author__ = 'lin'

import os
import threading
import sys
reload(sys)
sys.setdefaultencoding('utf8')
from utils.FileUtils import *

class S16:
	path = None
	instance = None

	locker = threading.Lock()

	#初始化
	def __init__(self):
		pass

	#获取单例对象
	@staticmethod
	def getInstance():
		S16.locker.acquire()
		try:
			if not S16.instance:
				S16.instance = S16()
			return S16.instance
		finally:
			S16.locker.release()

	#执行
	def getResult(self,path,appName,fileName,anotherName):
		result = {}
		if(not os.path.exists(path)):
			return result

		resultPath = os.path.join(path,'result.txt')
		if(os.path.exists(resultPath)):
			result['resultTable'] = simpleToTable(resultPath,True)

		return result

if __name__ == '__main__':
	S16 = S16.getInstance()
	re = S16.getResult('/Users/lin/23/1/16042700154449')
	print re['resultTable']
