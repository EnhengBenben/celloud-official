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
                        elif index == 3:
                            result['sampleNum'] = line
                        elif index == 4:
                            result['avgSampleSeq'] = line
                        elif index == 5:
                            result['variance'] = line
                        elif index == 6:
                            result['stdev'] = line
                        elif index == 7:
                            result['minSampleSeq'] = line
                        elif index == 8:
                            result['maxSampleSeq'] = line
                        elif index == 9:
                            result['less5000'] = line
                        elif index == 10:
                            result['more2000'] = line
                        index += 1
                    f.close()
                elif file == 'sample_statistic.xls':
                    f = open(os.path.join(resultPath, file), 'r')
                    r_list = []
                    for line in f.readlines():
                        st = line.strip().split('\t')
                        r_list.append({'name': list_value(st, 0), 'number': list_value(st, 1), 'avgQuality':  list_value(st, 2), 'avgGCContent':  list_value(st, 3)})
                    result['resultList'] = r_list
                    f.close()
                elif file == 'splitstat.xls':
                    splitstatPath = os.path.join(resultPath, file)
                    f = open(splitstatPath, 'r')
                    lines = f.readlines()
                    file_size = len(lines)
                    result['usefulReads'] = list_value(linecache.getline(splitstatPath, file_size).split('\t'), 1)
                    result['unknownReads'] = list_value(linecache.getline(splitstatPath, file_size-1).split('\t'), 1)
                    f.close()
        # QC
        qcPath = os.path.join(path, 'QC')
        if os.path.exists(qcPath):
            num = 1
            for file in os.listdir(qcPath):
                if file.endswith('_fastqc'):
                    fastqcPath = os.path.join(qcPath, file)
                    qcImgPath = os.path.join(fastqcPath, 'Images')
                    if os.path.exists(os.path.join(qcImgPath, 'per_base_quality.png')):
                        result['qualityPath'+str(num)] = '/QC/'+file+'/Images/'+'per_base_quality.png'
                    if os.path.exists(os.path.join(qcImgPath, 'per_base_sequence_content.png')):
                        result['seqContentPath'+str(num)] = '/QC/'+file+'/Images/'+'per_base_sequence_content.png'
                    qcDataPath = os.path.join(fastqcPath, 'fastqc_data.txt')
                    if os.path.exists(qcDataPath):
                        basicStatistics = dict()
                        basicStatistics['Filename'] = linecache.getline(qcDataPath, 4).split('\t')[1]
                        basicStatistics['FileType'] = linecache.getline(qcDataPath, 5).split('\t')[1]
                        basicStatistics['Encoding'] = linecache.getline(qcDataPath, 6).split('\t')[1]
                        basicStatistics['TotalSeq'] = linecache.getline(qcDataPath, 7).split('\t')[1]
                        basicStatistics['FilteredSeq'] = linecache.getline(qcDataPath, 8).split('\t')[1]
                        basicStatistics['SeqLength'] = linecache.getline(qcDataPath, 9).split('\t')[1]
                        basicStatistics['gc'] = linecache.getline(qcDataPath, 10).split('\t')[1]
                        result['basicStatistics'+str(num)] = basicStatistics
                    num += 1
        return result
