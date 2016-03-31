#!/usr/bin/python
# -*- coding: utf-8 -*-
# python version 2.7.6

__author__='lin'

#自动部署脚本

import os,shutil,time,sys
from mysql.mysqlpro import MySQLPro

if len(sys.argv) != 2:
	print 'Usage: *.py num'
	print 'num:1－－备份；2－－重启tomcat(删除celloud/ROOT文件夹)；3-－文件回拷；4－－仅重启tomcat；'
	sys.exit(0)

flag = sys.argv[1]

#today
ISOTIMEFORMAT='%Y%m%d'
today = time.strftime(ISOTIMEFORMAT)

#tomcat
tomcatPath = '/home/celloud/server/apache-tomcat-7.0.56'
tomcatStopCommand = 'shutdown.sh'
tomcatStartCommand = 'startup.sh'

#source path
celloudSource = os.path.join(tomcatPath,'webapps/celloud')
ROOT = os.path.join(tomcatPath,'webapps/ROOT')
toolsSource = '/share/data/webapps/Tools'
pythonSource = '/share/biosoft/perl/PGS_MG/python'

#bak path
bakPath = '/share/data/bak'
celloudBak = os.path.join(bakPath,today+'celloud')
toolsBak = os.path.join(bakPath,today+'Tools')
pythonBak = os.path.join(bakPath,today+'Python')
sqlBak = os.path.join(bakPath,today+'celloud.sql')

#copy back
pythonBack = ['app/PDFPro.py','mysql/mysqlpro.py','mongo/mongopro.py']

#celloud static
celloudStaticAppend = 'resources'
staticResultPath = '/share/data/celloud/resources'

if flag=='1':

	os.system('mysqldump -u'+MySQLPro.user+' -p'+MySQLPro.password+' -h'+MySQLPro.host+' '+MySQLPro.db+'>'+sqlBak)
	print '－－数据库备份完毕－－'
	shutil.copytree(celloudSource,celloudBak)
	print '－－celloud备份完毕－－'
	for x in os.listdir(toolsSource):
		if x != 'upload':
			s = os.path.join(toolsSource,x)
			if os.path.isdir(s):
				shutil.copytree(s,os.path.join(toolsBak,x))
			else:
				shutil.copy(s,os.path.join(toolsBak,x))
	print '－－Tools备份完毕－－'
	shutil.copytree(pythonSource,pythonBak)
	print '－－python备份完毕－－'

elif flag=='2':
	
	stop = os.path.join(tomcatPath,'bin',tomcatStopCommand)
	os.system(stop)
	print '－－tomcat 已停止－－'
	shutil.rmtree(celloudSource)
	print '－－celloud 文件夹已删除－－'
	shutil.rmtree(ROOT)
	print '－－ROOT 文件夹已删除－－'
	temp = os.path.join(tomcatPath,'work','Catalina')
	shutil.rmtree(temp)
	print '－－tomcat 缓存已删除－－'
	start = os.path.join(tomcatPath,'bin',tomcatStartCommand)
	os.system(start)
	print '－－tomcat 已启动－－'

elif flag=='3':

	shutil.rmtree(staticResultPath)
	staticFrom = os.path.join(celloudSource,celloudStaticAppend)
	shutil.copytree(staticFrom,staticResultPath)
	shutil.copytree(os.path.join(staticResultPath,'img'),os.path.join(staticResultPath,'images'))
	print '--celloud 静态资源拷贝完毕－－'
	for x in pythonBack:
		shutil.copyfile(os.path.join(pythonBak,x),os.path.join(pythonSource,x))
	print '--python 回拷完毕－－'

elif flag=='4':
	
	stop = os.path.join(tomcatPath,'bin',tomcatStopCommand)
	os.system(stop)
	print '－－tomcat 已停止－－'
	temp = os.path.join(tomcatPath,'work','Catalina')
	shutil.rmtree(temp)
	print '－－tomcat 缓存已删除－－'
	start = os.path.join(tomcatPath,'bin',tomcatStartCommand)
	os.system(start)
	print '－－tomcat 已启动－－'

else:
	print '错误的执行参数'
