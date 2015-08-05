#encoding=utf-8
#coding=utf-8

#导入pymongo模块（若没有，则安装:sudo pip install pymongo）
import pymongo
from pymongo import MongoClient

#默认host、port
#client=MongoClient()
#指定host、port
client=MongoClient('localhost', 27017)
#url连接
#client=MongoClient('mongodb://localhost:27017/')

#选择Database
db=client.test_database
#db=client['test-database']

#选择Collection
collection=db.test_collection
#collection=db['test-collection']

import datetime
post={"name":"over","description":"My third mongodb test!","tags":["mongodb","python","pymongo"],"date":datetime.datetime.utcnow()}

posts=db.posts
#insert并返回id
post_id=posts.insert_one(post).inserted_id
print post_id
#查询一条记录（第一条记录）
print posts.find_one()
#根据名字查询
print posts.find_one({"name": "test"})
#根据id查询
print posts.find_one({"_id": post_id})
#若将post_id转化成字符串则无法检索出结果
#post_id_as_str=str(post_id)
#posts.find_one({"_id": post_id_as_str})

#插入多条
new_posts = [{"author": "Mike","text": "Another post!","tags": ["bulk", "insert"],"date": datetime.datetime(2009, 11, 12, 11, 14)},{"author": "Eliot","title":"MongoDB is fun","text": "and pretty easy too!","date": datetime.datetime(2009, 11, 10, 10, 45)}]
result = posts.insert_many(new_posts)
print result.inserted_ids

#全查
posts.find()
#条件查询
posts.find({"author": "Mike"})

#统计
posts.count()
#带有条件的统计
posts.find({"author": "Mike"}).count()

#排序
d = datetime.datetime(2015, 8, 3, 8)
posts.find({"date": {"$lt": d}}).sort("author")
