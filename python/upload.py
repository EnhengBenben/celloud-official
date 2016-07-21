#!/usr/bin/python
# -*- coding: utf-8 -*-
# python version 2.7.6

#上传脚本
__author__='lin'

import paramiko,sys,os,shutil,datetime

if len(sys.argv) != 4:
    print 'isBackstage/isManager:0--不需要部署；1--需要部署'
    print 'Usage: *.py version isBackstage isManager'
    sys.exit(0)
version = sys.argv[1]
isBackstage = sys.argv[2]
isManager = sys.argv[3]

#服务器配置
host = '112.64.18.106'
port = 6013
username = 'celloud'
password = 'CelLoud@nova'
resultPath = '/share/data/deploy/' + version

#本地各项存储路径配置
celloudPath = '/Users/lin/Documents/cleangit/celloud/celloud/target/celloud.war'
backstagePath = '/Users/lin/Documents/cleangit/celloud/backstage/target/backstage.war'
managerPath = '/Users/lin/Documents/cleangit/celloud/manager/target/manager.war'
pythonPath = '/Users/lin/Documents/cleangit/celloud/python'
dbPath = os.path.join('/Users/lin/Documents/cleangit/celloud/dbUpgrade',version)
parentPath = '/Users/lin'

def upload(local_dir,remote_dir):
    try:
        t=paramiko.Transport((host,port))
        t.connect(username=username,password=password)
        sftp=paramiko.SFTPClient.from_transport(t)
        if(not local_dir.endswith(os.sep)):
			local_dir = local_dir + os.sep
        for root,dirs,files in os.walk(local_dir):
            for filespath in files:
                local_file = os.path.join(root,filespath)
                a = local_file.replace(local_dir,'')
                remote_file = os.path.join(remote_dir,a)
                try:
                    sftp.put(local_file,remote_file)
                except Exception,e:
                    sftp.mkdir(os.path.split(remote_file)[0])
                    sftp.put(local_file,remote_file)
            for name in dirs:
                local_path = os.path.join(root,name)
                a = local_path.replace(local_dir,'')
                remote_path = os.path.join(remote_dir,a)
                try:
                    sftp.mkdir(remote_path)
                except Exception,e:
                    print e
        t.close()
    except Exception,e:
        print e

print '文件整合： %s ' % datetime.datetime.now()
sourcePath = os.path.join(parentPath,version)
os.mkdir(sourcePath)
shutil.copy(celloudPath,sourcePath)

if(isBackstage=='1'):
    shutil.copy(backstagePath,sourcePath)

if(isManager=='1'):
    shutil.copy(managerPath,sourcePath)

shutil.copytree(pythonPath,os.path.join(sourcePath,'python'))
shutil.copytree(dbPath,os.path.join(sourcePath,'db'))
print '文件上传： %s ' % datetime.datetime.now()
upload(sourcePath,resultPath)
print '文件删除： %s ' % datetime.datetime.now()
shutil.rmtree(sourcePath)
print '过程结束： %s ' % datetime.datetime.now()
