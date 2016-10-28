#!/usr/bin/python
# -*- coding: utf-8 -*-
# python version 2.7.6

__des__ = 'ABINJ的操作类'
__author__ = 'lin'

import os
import threading
import sys
reload(sys)
sys.setdefaultencoding('utf8')

class ABINJ:
	path = None
	instance = None

	locker = threading.Lock()

	#初始化
	def __init__(self):
		pass

	#获取单例对象
	@staticmethod
	def getInstance():
		ABINJ.locker.acquire()
		try:
			if not ABINJ.instance:
				ABINJ.instance = ABINJ()
			return ABINJ.instance
		finally:
			ABINJ.locker.release()

	#执行
	def getResult(self,path):
		result = {}
		if(not os.path.exists(path)):
			return result

		for x in os.listdir(path):
			if(x == 'snp.fa.png'):
				result['resultPng'] = x

		return result

if __name__ == '__main__':
	ABINJ = ABINJ.getInstance()
	re = ABINJ.getResult('/Users/lin/23/11/9855')
	print re['result']
