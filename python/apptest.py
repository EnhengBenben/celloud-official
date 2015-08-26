#!/usr/bin/python
# -*- coding: utf-8 -*-
# python version 2.7.6

__des__='app测试类'
__author__='lin'

from app.NIPT import NIPT
nipt = NIPT.getInstance()
if nipt:
	res = nipt.getResult("/home/lin/20141211603127/")
	print res