#!/usr/bin/env python
# -*- coding: utf-8 -*-

__des__='project is run true'
__author__='lin'

#调用方式:python isTrue.py '/share/data1/webapps/Tools/upload/12/87/ProjectID4478.log ' '/share/data/webapps/Tools/upload/9/110' 'ProjectID3466'

import os , sys
from myemail.EmailSend import *

if len(sys.argv) != 4:
	print 'Usage: *.py log path projectId'
	sys.exit(0)
log = sys.argv[1]
with open(log, 'r') as f:
	lines = f.readlines()
	isRunTrue = False
	isErrorStart = False
	errorInfo = ""
	for i,line in enumerate(lines):
		#print i,line
		if(line.strip().startswith("==>end")):
			isErrorStart = True
			if(lines[i+1].strip().startswith("start")):
				isRunTrue = True
		#print isErrorStart
		#print isRunTrue
		if (isErrorStart and not isRunTrue):
			errorInfo += line +"<br>"
	if (not isRunTrue):
		path = sys.argv[2]
		if(not path.endswith(os.sep)):
			path = path+os.sep
		projectId = sys.argv[3]
		errorInfo += "python socket_client.py "+ path + " " + projectId
		send('linyongchao@celloud.cn','运行错误提醒',errorInfo)