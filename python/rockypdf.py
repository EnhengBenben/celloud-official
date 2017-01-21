#!/usr/bin/python
# -*- coding: utf-8 -*-
# python version 2.7.6

__author__='lin'

import os
import sys

from app.Rocky_PDF import *

#

if len(sys.argv) != 2:
	print 'Usage: *.py path projectId'
	sys.exit(0)
objId = sys.argv[1]
RockyPdf = RockyPdf.getInstance()
RockyPdf.createPDF(objId)



				