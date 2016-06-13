__author__ = 'leamox'

import os
from mongo.mongoOperate import mongo
from utils.StringUtils import *


def insert(path):
    if os.path.exists(path):
        mo = mongo.getInstance()
        mo.delete("BSISpecies")
        f = open(path, "r")
        for line in f.readlines():
            st = line.strip().split("\t")
            species = {"species": list_value(st, 0), "species_zh": list_value(st, 1)}
            objId = mo.put(species, "BSISpecies")
            print objId

if __name__ == '__main__':
	insert("/share/data/celloud/pairs_eng_chin_0612.txt")


