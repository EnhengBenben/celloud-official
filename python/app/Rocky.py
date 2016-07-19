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
        result_path = os.path.join(path, 'result')
        snp_path = os.path.join(result_path, "all.snp")
        print snp_path
        mo = mongo.getInstance()
        if os.path.exists(snp_path):
            f = open(snp_path, "r")
            for line in f.readlines():
                st = line.strip().split("\t")
                bases = st[2]
                acids = st[3]
                gene = st[4]
                description = st[6]
                mutation_1 = st[9]
                mutation_2 = st[10]
                rockyRecord={
                    "bases":bases,
                    "acids":acids,
                    "gene":gene,
                    "description":description,
                    "chromosome":mutation_1,
                    "position":mutation_2
                }
                rockyRecords.append(rockyRecord)
            f.close()
        result["records"] = rockyRecords
        return result



