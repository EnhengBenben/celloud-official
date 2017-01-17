#!/usr/bin/python
# -*- coding: utf-8 -*-

__des__ = "Rocky的操作类"
__author__ = "sun8wd"

import threading
import json
from mongo.mongoOperate import mongo
from utils.StringUtils import *
from utils.FileUtils import *
from app.MIB import MIB
from Rocky_PDF import *

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
    def getResult(self, path, appId, dataKey, userId, companyId):
        result = {}
        rockyRecords = [];
        descriptions = self.getDescription()
        result_path = os.path.join(path,str(dataKey),'result','all.snp')
        print result_path
        mo = mongo.getInstance()
        pathogenic = 'false'
        #rockyPdf = RockyPdf.getInstance()
        if os.path.exists(result_path):
            f = open(result_path, "r")
            for line in f.readlines():
                st = line.strip().split("\t")
                acids = st[2]
                gene = st[0]
                variationId = st[5]
                chromosome = st[1]
                significance = st[4]
                nucleotides = st[3]
                peakPic = st[6]
                description = '目前暂无描述说明。我们将保持关注，根据最新科研成果及时更新该状态。'
                if gene+'-'+variationId in descriptions.keys():
                    description = descriptions[gene+'-'+variationId]
                if significance=='pathogenic':
                    pathogenic='true'
                rockyRecord={
                    "acids":acids,
                    "variationId":variationId,
                    "gene":gene,
                    "description":description,
                    "chromosome":chromosome,
                    "significance":significance,
                    "nucleotides":nucleotides,
                    "peakPic":peakPic
                }
                if description == '-':
                    rockyRecord['description'] = "There is no description at this moment."
                rockyRecords.append(rockyRecord)
            #rockyPdf.createPDF(path, dataKey, userId, companyId)
            f.close()
        result["path"] = path
        result["records"] = rockyRecords
        result["pathogenic"] = pathogenic
        return result

    def getDescription(self):
        path = os.path.split(os.path.realpath(__file__))[0]
        path = os.path.join(path,'Rocky.txt')
        with open(path, 'r') as f:
            data = json.load(f)
        f.close()
        return data


