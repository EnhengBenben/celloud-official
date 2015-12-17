#!/usr/bin/python
# -*- coding: utf-8 -*-

__des__ = 'split的操作类'
__author__ = 'leamox'

import os
import threading
import linecache
from utils.StringUtils import *


class split:
    path = None
    instance = None
    locker = threading.Lock()
    # 初始化
    def __init__(self):
        pass

    # 获取单例对象
    @staticmethod
    def getInstance():
        split.locker.acquire()
        try:
            if not split.instance:
                split.instance = split()
            return split.instance
        finally:
            split.locker.release()

    # 执行
    def getResult(self, path, appId, dataKey):
        result = {}
        path = os.path.join(path, dataKey)
        if not os.path.exists(path):
            return result

        # result/statistic.xls
        resultPath = os.path.join(path, 'result')
        if os.path.exists(resultPath):
            for file in os.listdir(resultPath):
                if file == 'statistic.xls':
                    f = open(os.path.join(resultPath, file), 'r')
                    index = 0
                    for line in f.readlines():
                        if index == 0:
                            result['totalReads'] = line
                        elif index == 1:
                            result['avgQuality'] = line
                        elif index == 2:
                            result['avgGCContent'] = line
                        index += 1
                    f.close()
                elif file == 'splitstat.xls':
                    f = open(os.path.join(resultPath, file), 'r')
                    rlist = []
                    for line in f.readlines():
                        st = line.strip().split("\t")
                        rlist.append({'name': list_value(st, 0), 'number': list_value(st, 1)})
                    result['resultList'] = rlist
                    f.close()
        return result
