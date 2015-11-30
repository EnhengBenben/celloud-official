#!/usr/bin/python
# -*- coding: utf-8 -*-
# python version 2.7.6

__des__='String 工具类'
__author__='lin'

#get barcode
def getBarcode(fileName):
	f = fileName.split("_")
	if( len(f) >2 ):
		return f[0]+"_"+f[1]
	else:
		return fileName


# list取值
def list_value(list, index):
    if len(list) > index:
        return list[int(index)]
    else:
        return ''


# dict取值
def dict_value(dic, key):
    if dict(dic).has_key(key):
        return dic[str(key)]
    else:
        return ''

if __name__ == '__main__':
	print getBarcode('a.bam')
	print getBarcode('a1_b.bam')
	print getBarcode('a1_b_c.bam')
	print getBarcode('IonXpress_014_R_2015_08_18_22_32_22_user_SN2-108-2015-8-19-10samples-renxu__Auto_user_SN2-108-2015-8-19-10samples-renxu_369.bam')