#!/usr/bin/python
# -*- coding: utf-8 -*-

import os
import shutil

path = '/share/data/webapps/Tools/upload'
outPath = '/share/data/EGFR'

for user in os.listdir(path):
    ap = os.path.join(path,user,'84')
    if(os.path.exists(ap)):
        for data in os.listdir(ap):
            rp = os.path.join(ap,data,'report.txt')
            if(os.path.exists(rp)):
                op = os.path.join(outPath,user,'84',data)
                if not os.path.exists(op):
                    os.makedirs(op)
                shutil.copy(rp,op)


