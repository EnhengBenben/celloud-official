#!/usr/bin/python
# -*- coding: utf-8 -*-
# python version 2.7.6

__des__ = 'HBV create html'
__author__ = 'lin'

import shutil,os
from PDFPro import PDFPro
from utils.FileUtils import *

# create HBV html
def createHTML(path,fileName,type,other,result):
	# 0. 准备路径
	template = os.path.join(path,'HBV_SNP','SNP.html')
	folder = os.path.join(path,'HBV_SNP')
	# 1. 拷贝模版
	shutil.copytree(PDFPro.html,folder)
	# 2. 读取 HTML 模板
	context = readAll(template)
	# 3. 拷贝结果
	shutil.copytree(os.path.join(path,'SVG'),os.path.join(path,'HBV_SNP','result'))
	know = ['169.png','173.png','180.png','181.png','184.png','194.png','202.png','204.png','236.png','250.png']
	for png in know:
		if not os.path.exists(os.path.join(path,'HBV_SNP','result',png)):
			htmlPath = '<img style="padding-left: 30px;" src="./result/'+png+'" height="150px;" width="150px;">'
			context = context.replace(htmlPath,'')

	# 4. 替换需要替换的内容
	imgs = ''
	for key in other:
		imgs += "<img title='" + other[key]	+ "' style='padding-left: 30px;' src='./result/" + other[key] + "' height='150px;' width='150px;'>"

	context = context.replace('#fileName',fileName).replace('#type',type).replace('#other',imgs).replace('#result',result.replace('\n','<br/>').split('Other:')[0])
	# 5. 将结果写入文件
	f=open(os.path.join(path,'HBV_SNP','HBV_SNP.html'),'w')
	f.write(context)
	f.close()
	# 6. 删除模板
	os.remove(template)
	# 7. 压缩
	zip_dir(folder,os.path.join(path,'HBV_SNP.zip'))

if __name__ == '__main__':
	createHTML('/Users/lin/23/82/1','a','b','c','d')
