#!/usr/bin/python
# -*- coding: utf-8 -*-

__des__ = "BSI的操作类"
__author__ = "leamox"

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
        BSI.locker.acquire()
        try:
            if not BSI.instance:
                BSI.instance = BSI()
            return BSI.instance
        finally:
            BSI.locker.release()

    # 执行
    def getResult(self, path, appId, dataKey):
        result = {}
        result_path = os.path.join(path, 'result')
        snp_path = os.path.join(result_path, "all.snp")
        mo = mongo.getInstance()
        if os.path.exists(snp_path):
            f = open(snp_path, "r")
            species_20 = []
            for line in f.readlines():
                st = line.strip().split("\t")
                bases = list_value[st,1]
                acids = list_value[st,2]
                gene = list_value[st,3]
                description = list_value[st,5]
                mutation_1 = list_value[st,8]
                mutation_2 = list_value[st,9]
                result={
                    "bases":bases,
                    "acids":acids,
                    "gene":gene,
                    "description":description,
                    "chromosome":mutation_1,
                    "position":mutation_2
                }
            f.close()
        return result



