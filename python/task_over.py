#!/usr/bin/python
# -*- coding: utf-8 -*-

__author__ = 'leamox'
import os
import sys
import datetime
from mysql.mysqlOperate import mysql
from mongo.mongoOperate import mongo
from app.CMP import CMP
from app.split import split
from app.MIB import MIB
from app.BSI import BSI
from app.Rocky import Rocky
from app.AccuSeqa2 import AccuSeqa2

# command: python *.py basePath userId appId dataKey projectId
# eg : python task_over.py '/share/data/webapps/Tools/upload/' 88 110 20151119290394,20151119898677 proID
# eg : python e:/git/celloud/python/task_over.py d:/share/data/webapps/Tools/upload/ 88 110 20151119290394,20151119898677
# eg : python e:/git/celloud/python/task_over.py d:/share/data/webapps/Tools/upload/ 88 112 15112501860090,15112501860183

# 对应class名
method_dic = {110: CMP, 111: CMP, 112: CMP, 113: split, 114: MIB, 118: BSI, 123: Rocky, 126: CMP, 127: CMP, 128: CMP, 131: AccuSeqa2, 133: BSI, 134: BSI, 135: BSI, 136: BSI, 137: BSI}

# 对应database名
collection_dic = {110: "CmpReport", 111: "CmpReport", 112: "CmpReport", 113: "Split", 114: "MIB", 118: "BSI",
                  123: "Rocky", 126: "CmpReport", 127: "CmpReport", 128: "CmpReport", 131: "AccuSeqα2", 133: "BSI", 134: "BSI", 135: "BSI", 136: "BSI", 137: "BSI"}

if len(sys.argv) != 6:
    print 'Usage: *.py path userId appId dataKeys projectId'
    sys.exit(0)
path = sys.argv[1]
userId = sys.argv[2]
appId = sys.argv[3]
dataKeys = sys.argv[4]
dataKey = dataKeys.split(',')[0]
proId = sys.argv[5]

user_info_sql = "select c.company_id companyId,c.company_name companyName,c.english_name companyEngName,c.company_icon companyIcon,c.address companyAddr,c.address_en companyEnAddr,c.zip_code zipCode,c.tel companyTel,d.dept_name deptName,d.english_name deptEngName,d.dept_icon deptIcon,d.tel deptTel,u.user_id userId,u.username,u.email from tb_user u left join tb_dept d on u.dept_id=d.dept_id left join tb_company c on u.company_id=c.company_id where u.user_id="+userId
app_info_sql = "select app_id appId,app_name appName,title from tb_app where app_id="+appId
data_info_sql = "select file_id fileId,user_id userId,data_key dataKey,file_name fileName,another_name anotherName,strain,sample,data_tags dataTags,size,create_date createDate from tb_file where data_key in ("+dataKeys+")"
sample_info_sql = "select sample_id,user_id,order_id,sample_name,exper_sample_name,is_add,type,sindex,create_date,update_date,state,remark from tb_sample where sample_id = (select sample_id from tb_task where project_id = " + proId + ")"
myDB = mysql.getInstance()
print 1
if myDB:
    appFun = method_dic[int(appId)].getInstance()
    if appFun:
        user_dict = myDB.query(user_info_sql)[0]
        app_dict = myDB.query(app_info_sql)[0]
        data_dict = myDB.query(data_info_sql)
        base = dict(app_dict,**user_dict)
        base['data'] = data_dict
        base['dataKey'] = dataKey
        base['projectId'] = int(proId)
        base['createDate'] = datetime.datetime.now()
        if appId == '118':
            if(myDB.query(sample_info_sql) != null):
                sample_dict = myDB.query(sample_info_sql)[0]
                base['sample'] = sample_dict
        path = os.path.join(path, userId, appId)
        result = None
        if appId == '123':
            result = appFun.getResult(path, appId, dataKey, user_dict['userId'], user_dict['companyId'])
        else:
            result = appFun.getResult(path, appId, dataKey)
        result = dict(result, **base)
        moDB = mongo.getInstance()
        objId = moDB.put(result, collection_dic[int(appId)])
        print objId

