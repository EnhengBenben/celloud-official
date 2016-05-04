#!/usr/bin/python
# -*- coding: utf-8 -*-

__des__ = "BSI的操作类"
__author__ = "leamox"

import threading
from mongo.mongoOperate import mongo
from utils.StringUtils import *
from utils.FileUtils import *
from app.MIB import MIB

class BSI:
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
        result1 = MIB.getInstance().getResult(path, appId, dataKey)
        result = dict(result, **result1)
        path = os.path.join(path, dataKey)
        if not os.path.exists(path):
            return result
        result_path = os.path.join(path, 'result')
        normal_species_path = os.path.join(result_path, "20.species")
        mo = mongo.getInstance()
        if os.path.exists(normal_species_path):
            f = open(normal_species_path, "r")
            species_20 = []
            for line in f.readlines():
                st = line.strip().split("\t")
                specie_name = list_value(st, 0)
                cross_species = list_value(st, 10)
                strain_dict = mo.get({"species": str(specie_name)}, "BSISpecies")
                species_zh = ""
                if strain_dict is not None:
                    species_zh = dict_value(strain_dict, "species_zh")
                cross_species_dict = mo.get({"species": str(cross_species)}, "BSISpecies")
                cross_species_zh = ""
                if cross_species_dict is not None:
                    cross_species_zh = dict_value(cross_species_dict, "species_zh")
                species = {"species": specie_name, "species_zh": species_zh, "site1": list_value(st, 1),
                           "site2": list_value(st, 2), "site3": list_value(st, 3), "site4": list_value(st, 4),
                           "unique_reads_num": list_value(st, 5), "seq1": list_value(st, 6), "seq2": list_value(st, 7),
                           "seq3": list_value(st, 8), "seq4": list_value(st, 9), "cross_species": cross_species,
                           "cross_species_zh": cross_species_zh
                           }
                species_20.append(species)
            result["species_20"] = species_20
            f.close()
        other_species_path = os.path.join(result_path, "other.species")
        if os.path.exists(other_species_path):
            f = open(other_species_path, "r")
            species_other = []
            for line in f.readlines():
                st = line.strip().split("\t")
                species = {"species": list_value(st, 0), "site1": list_value(st, 1),
                           "site2": list_value(st, 2), "site3": list_value(st, 3), "site4": list_value(st, 4),
                           "unique_reads_num": list_value(st, 5), "seq1": list_value(st, 6), "seq2": list_value(st, 7),
                           "seq3": list_value(st, 8), "seq4": list_value(st, 9)
                           }
                species_other.append(species)
            result["species_other"] = species_other
            f.close()
        return result



