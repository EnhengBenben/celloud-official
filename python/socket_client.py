#!/usr/bin/env python
# -*- coding: utf-8 -*-

__des__='socket client'
__author__='lin'

#调用方式:python socket_client.py '/share/data/webapps/Tools/upload/9/110' 'ProjectID3466'

import socket , time , os , sys

if len(sys.argv) != 3:
	print 'Usage: *.py path projectId'
	sys.exit(0)
path = sys.argv[1]
if(not path.endswith(os.sep)):
	path = path+os.sep
projectId = sys.argv[2].replace('ProjectID','')
path = os.path.join(path , projectId)

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
# 建立连接:
s.connect(('112.64.18.107', 8088))
print s.recv(1024)
s.send(path)
print s.recv(1024)
s.send('exit')
s.close()