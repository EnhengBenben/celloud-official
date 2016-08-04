#!/usr/bin/python
# -*- coding: utf-8 -*-

__des__ = "Rocky的操作类"
__author__ = "sun8wd"

import threading
from mongo.mongoOperate import mongo
from utils.StringUtils import *
from utils.FileUtils import *
from app.MIB import MIB

class Rocky:
    path = None
    instance = None
    locker = threading.Lock()
    # 初始化
    def __init__(self):
        pass

    # 获取单例对象
    @staticmethod
    def getInstance():
        Rocky.locker.acquire()
        try:
            if not Rocky.instance:
                Rocky.instance = Rocky()
            return Rocky.instance
        finally:
            Rocky.locker.release()

    # 执行
    def getResult(self, path, appId, dataKey):
        result = {}
        rockyRecords = [];
        result_path = os.path.join(path,dataKey, 'result','all.snp')
        print result_path
        mo = mongo.getInstance()
        pathogenic = 'false'
        if os.path.exists(result_path):
            f = open(result_path, "r")
            for line in f.readlines():
                st = line.strip().split("\t")
                acids = st[2]
                gene = st[0]
                description = st[5]
                chromosome = st[1]
                significance = st[4]
                nucleotides = st[3]
                if significance=='pathogenic':
                    pathogenic='true'
                print pathogenic
                rockyRecord={
                    "acids":acids,
                    "gene":gene,
                    "description":description,
                    "chromosome":chromosome,
                    "significance":significance,
                    "nucleotides":nucleotides
                }
                if description == '-':
                    rockyRecord['description'] = "There is no description at this moment."
                rockyRecords.append(rockyRecord)
            f.close()
        result["records"] = rockyRecords
        result["pathogenic"] = pathogenic
        return result


