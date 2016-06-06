#!/usr/bin/python
# -*- coding: utf-8 -*-
# python version 2.7.6

__des__='mongodb的操作类'
__author__='lin'

import pymongo
from pymongo import MongoClient
import threading
from mongopro import MongoPro

class mongo:
	client = None
	db = None
	collection = None

	instance = None

	locker = threading.Lock()

	@staticmethod
	def _connect():
		if not mongo.client:
			mongo.client = MongoClient(MongoPro.host, MongoPro.port) # 建立mongodb的连接
			mongo.db = mongo.client[MongoPro.db]  #连接到指定的数据库中

	#初始化
	def __init__(self):
		mongo._connect()

	#获得单列对象
	@staticmethod
	def getInstance():
		mongo.locker.acquire()
		try:
			if not mongo.instance:
				mongo.instance = mongo()
			return mongo.instance
		finally:
			mongo.locker.release()

	#写入
	def put(self, result , collection):
		self.collection = self.db[collection] #连接到具体的collection中
		try:
			objId = self.collection.insert_one(result).inserted_id
			return objId
		except Exception as e:
			print "mongo insert Exception ==>> %s " % e
		finally:
			mongo.client = None

	#批量写入
	def insertBatch(self, result , collection):
		self.collection = self.db[collection]; #连接到具体的collection中
		try:
			self.collection.insert(result);
		except Exception as e:
			print "mongo insertBatch Exception ==>> %s " % e;
		finally:
			mongo.client = None;

	#查询
	def get(self, condition , collection):
		self.collection = self.db[collection] #连接到具体的collection中
		try:
			result = self.collection.find_one(condition)
			return result
		except Exception as e:
			print "mongo find Exception ==>> %s " % e
		finally:
			mongo.client = None

	#根据条件查询所有
	def findAllByCondition(self, condition, collection):
		self.collection = self.db[collection]; #连接到具体的collection中
		try:
			result = self.collection.find(condition);
			return result;
		except Exception as e:
			print "mongo findAllByCondition Exception ==>> %s " % e;
		finally:
			mongo.client = None;

	#根据条件删除所有
	def deleteAllByCondition(self, condition, collection):
		self.collection = self.db[collection]; #连接到具体的collection中
		try:
			self.collection.remove(condition);
		except Exception as e:
			print "mongo delete Exception ==>> %s " % e;
		finally:
			self.client = None;
	
	#删除
	def delete(self, collection):
		self.collection = self.db[collection] #连接到具体的collection中
		try:
			self.collection.remove();
		except Exception as e:
			print "mongo delete Exception ==>> %s " % e;
		finally:
			self.client = None;