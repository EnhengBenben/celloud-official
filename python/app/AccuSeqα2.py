#!/usr/bin/python
# -*- coding: utf-8 -*-

__des__ = 'AccuSeqα2的操作类'
__author__ = 'leamox'

import os
import threading
import linecache
from mongo.mongoOperate import mongo
from utils.StringUtils import *


class AccuSeqα2:
    path = None
    instance = None
    locker = threading.Lock()
    # 初始化
    def __init__(self):
        pass

    # 获取单例对象
    @staticmethod
    def getInstance():
        AccuSeqα2.locker.acquire()
        try:
            if not AccuSeqα2.instance:
                AccuSeqα2.instance = AccuSeqα2()
            return AccuSeqα2.instance
        finally:
            AccuSeqα2.locker.release()

    # 执行
    def getResult(self, path, appId, dataKey):
        result = {}
        path = os.path.join(path, dataKey)
        if not os.path.exists(path):
            return result

        positive = os.path.join(path)
        # /positive.lis
        positive_path = os.path.join(path, 'positive.lis')
        if os.path.exists(positive_path):
            f = open(positive_path, 'r')
            result_gdd = []
            for line in f.readlines():
                gsr = line.strip().split("\t")
                gene = list_value(gsr, 0).decode('gbk')
                gsr_d = {'gene': gene, 'refBase': list_value(gsr, 1), 'mutBase': list_value(gsr, 2), 'depth': list_value(gsr, 3), 'cdsMutSyntax': list_value(gsr, 4), 'aaMutSyntax': list_value(gsr, 5), 'mutationType': list_value(gsr, 6)}
                result_gdd.append(gsr_d)
            f.close()
            result['usefulGeneResult'] = result_gdd
        result_a = self.commonInfo(os.path.join(path, 'A'))
        result_b = self.commonInfo(os.path.join(path, 'B'))
        result['dataAReport'] = result_a
        result['dataBReport'] = result_b
        return result

    def commonInfo(self, path):
        result = {}
        if not os.path.exists(path):
            return result

        # result/statistic.xls
        resultPath = os.path.join(path, 'result')
        if os.path.exists(resultPath):
            geneDetectionDetail = {}
            for file in os.listdir(resultPath):
                if file == 'statistic.xls':
                    f = open(os.path.join(resultPath, file), 'r')
                    index = 0
                    for line in f.readlines():
                        if index == 0:
                            result['allFragment'] = line
                        elif index == 1:
                            result['avgQuality'] = line
                        elif index == 2:
                            result['avgGCContent'] = line
                        elif index == 3:
                            result['usableFragment'] = line
                        elif index == 4:
                            result['noDetectedGene'] = line
                        elif index == 5:
                            result['detectedGene'] = line
                        index += 1
                    f.close()
                elif file == 'average.info':
                    f = open(os.path.join(resultPath, file),'r')
                    result['avgCoverage'] = f.readline()
                    f.close()
                elif file == 'snp_num.info':
                    f = open(os.path.join(resultPath, file),'r')
                    cmpGeneResult = []
                    for line in f.readlines():
                        gdrs = line.strip().split("\t")
                        cmpGeneResult.append({'geneName': list_value(gdrs, 0), 'knownMSNum': list_value(gdrs, 1), 'sequencingDepth': list_value(gdrs, 2)})
                    result['cmpGeneResult'] = cmpGeneResult
                    f.close()
                else:
                    gdd = {}
                    f = open(os.path.join(resultPath, file),'r')
                    avgSeqDepth = ''
                    result_gdd = []
                    index = 0
                    for line in f.readlines():
                        if index == 0 and file != 'all.snp':
                            avgSeqDepth = line
                            index += 1
                        else:
                            gsr = line.strip().split("\t")
                            gene = list_value(gsr, 0).decode('gbk')
                            gsr_d = {'gene': gene, 'refBase': list_value(gsr, 1), 'mutBase': list_value(gsr, 2), 'depth': list_value(gsr, 3), 'cdsMutSyntax': list_value(gsr, 4), 'aaMutSyntax': list_value(gsr, 5), 'mutationType': list_value(gsr, 6)}
                            result_gdd.append(gsr_d)
                    f.close()
                    geneDetectionDetail[file.split('.')[0]] = {'avgCoverage': avgSeqDepth, 'result': result_gdd}
            result['geneDetectionDetail'] = geneDetectionDetail

        # /LOG.txt
        logPath = os.path.join(path, 'LOG.txt')
        if os.path.exists(logPath):
            f = open(logPath, 'r')
            result['runDate'] = f.readline()
            f.close()
        # QC
        qcPath = os.path.join(path, 'QC')
        if os.path.exists(qcPath):
            num = 1
            qc_list=os.listdir(qcPath)
            qc_list.sort(key=lambda fn: os.path.getmtime(qcPath+fn) if os.path.isdir(qcPath+fn) else 0)

            for file in qc_list:
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
