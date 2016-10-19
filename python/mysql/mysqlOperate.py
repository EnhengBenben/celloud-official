#!/usr/bin/python
# -*- coding: utf-8 -*-
# python version 2.7.6

__des__='mysql数据库操作文件'
__author__='lin'

import sys
import threading
import MySQLdb
from mysqlpro import MySQLPro

class mysql:
	conn = None
	db = None
	cur = None
	instance = None

	locker = threading.Lock()

	def _connect():
		if not mysql.conn:
			#获取数据库连接
			mysql.conn=MySQLdb.connect(host=MySQLPro.host,user=MySQLPro.user,passwd=MySQLPro.password,charset='utf8')
			#选择数据库
			mysql.db=mysql.conn.select_db(MySQLPro.db)
			#获取操作游标
			mysql.cur=mysql.conn.cursor()

	#初始化
	def __init__(self):
		reload(sys)
		sys.setdefaultencoding('utf-8')
		#mysql._connect()

	#获取单例对象
	@staticmethod
	def getInstance():
		mysql.locker.acquire()
		try:
			if not mysql.instance:
				mysql.instance = mysql()
			return mysql.instance
		finally:
			mysql.locker.release()

	#执行sql
	def execute(self,sql):
		try:
			#获取数据库连接
			self.conn=MySQLdb.connect(host=MySQLPro.host,user=MySQLPro.user,passwd=MySQLPro.password,charset='utf8')
			#选择数据库
			self.db=self.conn.select_db(MySQLPro.db)
			#获取操作游标
			self.cur=self.conn.cursor()
			#执行
			self.cur.execute(sql)
			#提交事物
			self.conn.commit()
		except MySQLdb.Error,e:
			print "Mysql Error %d: %s" % (e.args[0], e.args[1])
		finally:
			#关闭连接
			self.cur.close()
			self.conn.close()
	#执行sql
	def query(self,sql):
		try:
			#获取数据库连接
			self.conn=MySQLdb.connect(host=MySQLPro.host,user=MySQLPro.user,passwd=MySQLPro.password,charset='utf8')
			#选择数据库
			self.db=self.conn.select_db(MySQLPro.db)
			#获取操作游标
			self.cur=self.conn.cursor(cursorclass = MySQLdb.cursors.DictCursor)
			self.cur.execute(sql)
			return self.cur.fetchall()
		except MySQLdb.Error,e:
			print "Mysql Error %d: %s" % (e.args[0], e.args[1])
		finally:
			#关闭连接
			self.cur.close()
			self.conn.close()