#!/usr/bin/python
# -*- coding: utf-8 -*-
# python version 2.7.6

__des__='将之前的 NIPT 数据报告插入mongodb'
__author__='lin'

import os
from mysql.mysqlOperate import mysql
from data_insert import *

basePath = '/share/data/webapps/Tools/upload/'
sql = "select user_id,software_id,project_id from tb_report where software_id =82 and flag = 1;"
my=mysql.getInstance()
if my:
	result = my.query(sql)
	for i in range(len(result)):
		re = result[i]
		path = os.path.join(basePath , str(re['user_id']) , str(re['software_id']))
		datainsert(path,str(re['project_id']))