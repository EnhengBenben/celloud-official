#!/usr/bin/python
# -*- coding: utf-8 -*-

__des__ = 'MIB的操作类'
__author__ = 'leamox'

import os
import threading
import linecache
from utils.StringUtils import *
from utils.FileUtils import *


class MIB:
    path = None
    instance = None
    locker = threading.Lock()
    # 初始化
    def __init__(self):
        pass

    # 获取单例对象
    @staticmethod
    def getInstance():
        MIB.locker.acquire()
        try:
            if not MIB.instance:
                MIB.instance = MIB()
            return MIB.instance
        finally:
            MIB.locker.release()

    # 执行
    def getResult(self, path, appId, dataKey):
        result = {}
        path = os.path.join(path, dataKey)
        print path
        if not os.path.exists(path):
            return result
        print 33
        # 样品中属层次上reads的比例数据信息（前10）
        genusTop10Path = os.path.join(path, 'all.fastq.genus.top15')
        if os.path.exists(genusTop10Path):
            f = open(genusTop10Path, 'r')
            genusDistributionInfo = []
            for line in f.readlines():
                reads = line.strip().split("\t")
                genusDistributionInfo.append(reads)
            result['genusDistributionInfo'] = genusDistributionInfo
            print genusDistributionInfo
            f.close()
        # result
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
                # 测序结果
                elif file == 'conclusion.txt':
                    conclusion = readAllChinese(os.path.join(resultPath, file))
                    result['conclusion'] = conclusion
                elif file == 'taxi.all.fastq.table':
                    f = open(os.path.join(resultPath, file), 'r')
                    index = 0
                    summaryTable = []
                    for line in f.readlines():
                        if index > 0:
                            st = line.strip().split("\t")
                            info = {'Species': list_value(st, 0), 'Genus': list_value(st, 1), 'GI': list_value(st, 2), 'Coverage': list_value(st, 3), 'Reads_hit': list_value(st, 4), 'Reads_num': list_value(st, 5), 'Reads_Ratio': list_value(st, 6), 'avgCoverage': list_value(st, 7)}
                            summaryTable.append(info)
                        index += 1
                    result['summaryTable'] = summaryTable
                    f.close()
                # 低质量序列、宿主序列、16S序列以及未能比对上的reads比例数据信息（第一个饼图）
                elif file == 'all.fastq.reads_distribution':
                    f = open(os.path.join(resultPath, file), 'r')
                    readsDistributionInfo = []
                    for line in f.readlines():
                        reads = line.strip().split("\t")
                        readsDistributionInfo.append(reads)
                    result['readsDistributionInfo'] = readsDistributionInfo
                    f.close()
                # 16s相关的序列在科层次上的比例数据信息
                elif file == 'family.distribution.lis':
                    f = open(os.path.join(resultPath, file), 'r')
                    familyDistributionInfo = []
                    for line in f.readlines():
                        reads = line.strip().split("\t")
                        familyDistributionInfo.append(reads)
                    result['familyDistributionInfo'] = familyDistributionInfo
                    f.close()
                elif file == 'family_distribution.all.fastq.png':
                    result['familyDistribution'] = '/result/family_distribution.all.fastq.png'
                elif file == 'genus_distribution.all.fastq.png':
                    result['genusDistribution'] = '/result/genus_distribution.all.fastq.png'
                elif file == 'reads_distribution.all.fastq.png':
                    result['readsDistribution'] = '/result/reads_distribution.all.fastq.png'
                elif file == 'coverage_map_top10':
                    coveragePath = os.path.join(resultPath, 'coverage_map_top10')
                    pngPath = {}
                    for f in os.listdir(coveragePath):
                        p_key = ''
                        if f == 'top1.all.fastq.png':
                            p_key = 'top1png'
                        elif f == 'top2.all.fastq.png':
                            p_key = 'top2png'
                        elif f == 'top3.all.fastq.png':
                            p_key = 'top3png'
                        elif f == 'top4.all.fastq.png':
                            p_key = 'top4png'
                        elif f == 'top5.all.fastq.png':
                            p_key = 'top5png'
                        elif f == 'top6.all.fastq.png':
                            p_key = 'top6png'
                        elif f == 'top7.all.fastq.png':
                            p_key = 'top7png'
                        elif f == 'top8.all.fastq.png':
                            p_key = 'top8png'
                        elif f == 'top9.all.fastq.png':
                            p_key = 'top9png'
                        elif f == 'top10.all.fastq.png':
                            p_key = 'top10png'
                        pngPath[p_key] = '/result/coverage_map_top10/' + f
                    result['pngPath'] = pngPath
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