#!/usr/bin/python
# -*- coding: utf-8 -*-
# python version 2.7.6

__author__='lin'

#自动部署脚本

import os,shutil,time,sys
from mysql.mysqlpro import MySQLPro
from utils.FileUtils import *

if len(sys.argv) != 3:
	print 'Usage: *.py ip version'
	print 'ip:106－－备份、数据库、拷贝war包、重启tomcat、拷贝python、静态资源回拷；107－－拷贝war包、重启tomcat；999－－仅重启tomcat'
	print 'version:版本号'
	print 'eg:python deploy.py 106 3.1.12'
	sys.exit(0)

flag = sys.argv[1]
version = sys.argv[2]

#war/python/db
resultPath = '/share/data/deploy/' + version

#today
ISOTIMEFORMAT='%Y%m%d'
today = time.strftime(ISOTIMEFORMAT)

#tomcat
tomcatPath = '/home/celloud/server/apache-tomcat-7.0.56'
webappsPath = os.path.join(tomcatPath,'webapps')
tomcatStopCommand = os.path.join(tomcatPath,'bin','shutdown.sh')
tomcatStartCommand = os.path.join(tomcatPath,'bin','startup.sh')

#source path
celloudSource = os.path.join(webappsPath,'celloud')
celloudWar = os.path.join(webappsPath,'celloud.war')
ROOT = os.path.join(webappsPath,'ROOT')
#toolsSource = '/share/data/webapps/Tools'
pythonSource = '/share/biosoft/perl/PGS_MG/python'

#bak path
bakPath = '/share/data/bak'
celloudBak = os.path.join(bakPath,today+'celloud')
toolsBak = os.path.join(bakPath,today+'Tools')
pythonBak = os.path.join(bakPath,today+'Python')
sqlBak = os.path.join(bakPath,today+'celloud.sql')

#celloud static
celloudStaticAppend = 'resources'
staticResultPath = '/share/data/celloud/resources'

def restartTomcat():
	os.system(tomcatStopCommand)
	time.sleep(5)
	print '－－tomcat 已停止－－'
	shutil.rmtree(celloudSource)
	print '－－celloud 文件夹已删除－－'
	os.remove(celloudWar)
	print '－－celloud.war 已删除－－'
	if os.path.exists(ROOT):
		shutil.rmtree(ROOT)
		print '－－ROOT 文件夹已删除－－'
	temp = os.path.join(tomcatPath,'work','Catalina')
	if os.path.exists(temp):
		shutil.rmtree(temp)
		print '－－tomcat 缓存已删除－－'
	for f in os.listdir(resultPath):
		if f.endswith('.war'):
			shutil.copy(os.path.join(resultPath,f),webappsPath)
			print '－－' + f + '已拷贝－－'

	os.system(tomcatStartCommand)
	print '－－tomcat 正在启动－－'
	time.sleep(40)

	print '－－－－－－－－tomcat 启动结束－－－－－－－－'

if flag=='106':

	os.system('mysqldump -u'+MySQLPro.user+' -p'+MySQLPro.password+' -h'+MySQLPro.host+' '+MySQLPro.db+'>'+sqlBak)
	print '－－数据库备份完毕－－'
	shutil.copytree(celloudSource,celloudBak)
	print '－－celloud备份完毕－－'
	#for x in os.listdir(toolsSource):
	#	if x != 'upload':
	#		s = os.path.join(toolsSource,x)
	#		if os.path.isdir(s):
	#			shutil.copytree(s,os.path.join(toolsBak,x))
	#		else:
	#			shutil.copy(s,os.path.join(toolsBak,x))
	#print '－－Tools备份完毕－－'
	shutil.copytree(pythonSource,pythonBak)
	print '－－python备份完毕－－'

	print '－－－－－－－－备份结束－－－－－－－－'

	dbPath = os.path.join(resultPath,'db')
	for f in os.listdir(dbPath):
		if f.endswith('.sql'):
			for sql in readAllByLines(os.path.join(dbPath,f)):
				mysql = mysql.getInstance()
				if mysql:
					mysql.execute(sql)
			print '－－'+ f + '导入完毕－－'
			#使用source中文会乱码，原因不明
			#command = 'source ' + os.path.join(dbPath,f)
			#os.system('mysql -u'+MySQLPro.user+' -p'+MySQLPro.password+' -h'+MySQLPro.host+' '+MySQLPro.db+' -e "'+command+'"')
			#print '－－'+ command + '导入完毕－－'

	print '－－－－－－－－数据库导入结束－－－－－－－－'

	restartTomcat()

	pythonPath = os.path.join(resultPath,'python')
	shutil.rmtree(pythonSource)
	shutil.copytree(pythonPath,pythonSource)
	print '－－python 拷贝完毕－－'

	shutil.rmtree(staticResultPath)
	staticFrom = os.path.join(celloudSource,celloudStaticAppend)
	shutil.copytree(staticFrom,staticResultPath)
	shutil.copytree(os.path.join(staticResultPath,'img'),os.path.join(staticResultPath,'images'))
	print '－－celloud 静态资源拷贝完毕－－'
	
	print '－－－－－－－－106 部署结束－－－－－－－－'

elif flag=='107':
	
	restartTomcat()

	print '－－－－－－－－107 部署结束－－－－－－－－'

elif flag=='999':
	
	os.system(tomcatStopCommand)
	time.sleep(5)
	print '－－tomcat 已停止－－'
	temp = os.path.join(tomcatPath,'work','Catalina')
	if os.path.exists(temp):
		shutil.rmtree(temp)
		print '－－tomcat 缓存已删除－－'
	os.system(tomcatStartCommand)
	print '－－tomcat 已启动－－'

else:
	print '错误的执行参数'
