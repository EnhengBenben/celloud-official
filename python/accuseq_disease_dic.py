#!/usr/bin/python
# -*- coding: utf-8 -*-
# python version 2.7.6

__des__ = '增加AccuSeq-exon所需的疾病基因字典'
__author__ = 'leamox'

import os
from mongo.mongoOperate import mongo
from utils.StringUtils import *


def insert(path):
    if os.path.exists(path):
        mo = mongo.getInstance()
        mo.delete("AccuGddDiseaseDict")
        f = open(path, "r")
        for line in f.readlines():
            st = line.split("\t")
            species = {"gene": list_value(st, 0), "name": list_value(st, 1), "engName": list_value(st, 2)}
            objId = mo.put(species, "AccuGddDiseaseDict")
            print objId

if __name__ == '__main__':
	insert("accuseq.txt")