#!/usr/bin/python
# -*- coding: utf-8 -*-
# python version 2.7.6

__des__='文件操作工具类'
__author__='lin'

import codecs,os
import zipfile
import sys
reload(sys)
sys.setdefaultencoding('utf8')

#统计文件行数
def countLines(path):
	for count,line in enumerate(open(path,'rU')):
		pass
	return count + 1

#按行读取文件内容
def readAllByLines(path):
	context = []
	if not os.path.exists(path):
		return context
	with open(path,'r') as f:
		while 1:
			line = f.readline().strip()
			if not line:
				break
			context.append(line)
	return context

#读取文件内容
def readAll(path):
	with open(path,'r') as f:
		return f.read().strip()

#读取中文文件内容
def readAllChinese(path):
	with codecs.open(path,'r','gbk') as f:
		return f.read().strip()
##文件检索
##path：要检索的路径
##regulation：要匹配的字符串
##mate：匹配方式，支持 endWith , startWith , contains 三种，默认 contains
##未完成，待续
def fileSearch(path,regulation,mate):
	files = []
	if os.path.exists(path):
		for x in os.listdir(path):
			if(x.endswith(regulation)):
				files.append(x)
	files.sort()
	return files

def zip_dir(dirname,zipfilename):
    filelist = []
    if os.path.isfile(dirname):
        filelist.append(dirname)
    else :
        for root, dirs, files in os.walk(dirname):
            for name in files:
                filelist.append(os.path.join(root, name))
         
    zf = zipfile.ZipFile(zipfilename, "w", zipfile.zlib.DEFLATED)
    for tar in filelist:
        arcname = tar[len(dirname):]
        #print arcname
        zf.write(tar,arcname)
    zf.close()

def appendWrite(path,context):
	if not os.path.isdir(path):
		f = open(path, 'a')
		f.write(context)
		f.close()
	else:
		print 'not a file'

normalClass = ' class="table table-bordered table-condensed"'

def simpleToTable(path,isClass):
	if not os.path.isfile(path):
		print '文件不存在：' + path
	else:
		context = '<table'+ (normalClass if isClass else '') +'>'
		for line in open(path,'rU'):
			context += '<tr>'
			for l in line.split('\t'):
				context += '<td>'+l.replace('\n','')+'</td>'
			context += '</tr>'
		context += '</table>'
		return context


if __name__ == '__main__':
	#print countLines('/home/lin/20150817151721/SVG/Report.txt')
	#print readAll('/Users/lin/9/82/20151029437617/SVG/Report.txt')
	#print '--------'
	#print readAllChinese('/Users/lin/9/82/20151029437617/SVG/Report.txt').replace('Other:	Y','')
	#print fileSearch('/Users/lin/9/82/20151029437617/SVG','_new.png','endswith')
	#print fileSearch('/Users/lin/9/82/20151029437617/SVG','_all.png','endswith')
	#appendWrite('/Users/lin/test3','a\tb\tc\td\na\tb\tc\td\na\tb\tc\td\n')
	print simpleToTable('/Users/lin/test3.txt',True)
	print simpleToTable('/Users/lin/test3.txt',False)
	p = False
	print isinstance(p,(int,float))
