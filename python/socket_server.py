#!/usr/bin/env python
# -*- coding: utf-8 -*-

import time , socket , threading , os
from runover import *
import httplib

def tcplink(sock, addr):
    print 'Accept new connection from %s:%s...' % addr
    sock.send('start')
    while True:
        data = sock.recv(1024)
        if data == 'exit' or not data:
            break
        path = os.path.split(data)[0]
        projectId = os.path.split(data)[1]
        appId = os.path.split(path)[1]
        runover(path,appId,projectId)
        print data
        pgsdata(path,projectId)

        httpClient = None

        try:
            httpClient = httplib.HTTPConnection('localhost',8080,timeout=30)
            httpClient.request('GET','/celloud/project!runQueue?projectId='+projectId)
            response = httpClient.getresponse()
            print response.status
            print response.reason
            print response.read()
        except Exception, e:
            print e
        finally:
            if httpClient:
                httpClient.close()

        sock.send('over')
    sock.close()
    print 'Connection from %s:%s closed.' % addr

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind(('192.168.253.51', 8088))
s.listen(5)
print 'Waiting for connection...'
while True:
    # 接受一个新连接:
    sock, addr = s.accept()
    # 创建新线程来处理TCP连接:
    t = threading.Thread(target=tcplink, args=(sock, addr))
    t.start()