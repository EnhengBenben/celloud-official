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
        descriptions = self.getDescription()
        result_path = os.path.join(path,dataKey, 'result','all.snp')
        print result_path
        mo = mongo.getInstance()
        if os.path.exists(result_path):
            f = open(result_path, "r")
            for line in f.readlines():
                st = line.strip().split("\t")
                bases = st[19]
                acids = st[20]
                gene = st[0]
                description = st[22]+st[24]
                mutation_1 = st[1]
                mutation_2 = st[2]
                rockyRecord={
                    "bases":bases,
                    "acids":acids,
                    "gene":gene,
                    "description":"",
                    "chromosome":mutation_1,
                    "position":mutation_2
                }
                if bases in descriptions:
                    rockyRecord['description'] = descriptions[bases]
                rockyRecords.append(rockyRecord)
            f.close()
        result["records"] = rockyRecords
        return result
    def getDescription(self):
        path = os.path.split(os.path.realpath(__file__))[0]
        path = os.path.join(path,'Rocky.txt')
        print path
        f = open(path,'r')
        result = {}
        key = ''
        value = ''
        for line in f.readlines():
            pos = line.find('>>')
            if pos == 0:
                result[key] = value
                key = line[2:].strip()
                value = ''
            else:
                value += line.strip()
                value += '\n'
        f.close()
        return result



