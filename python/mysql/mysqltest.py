#!/usr/bin/python
# -*- coding: utf-8 -*-
# python version 2.7.6

__des__='mysql数据库测试文件'
__author__='lin'

from mysqlOperate import mysql
my=mysql.getInstance()
if my:
	my.execute("delete from tb_software where software_id = 111")
