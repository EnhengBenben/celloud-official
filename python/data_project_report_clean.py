__author__ = 'leamox'

import MySQLdb

conn = MySQLdb.connect(host='192.168.100.103', user='root', passwd='celloud',db="celloud", charset='utf8')
num = 0
with conn:
    curs = conn.cursor()
    curs.execute('select file_id,project_id from tb_file_project_relat where (file_id,project_id) not in (select file_id,project_id from tb_report where flag=0)')
    datas = curs.fetchall()
    for d in datas:
        fileId = str(d[0])
        projectId = str(d[1])
        curs.execute('select user_id,app_id,period,state from tb_report where flag=1 and project_id = '+projectId)
        proInfo = curs.fetchone()
        userId = str(proInfo[0])
        appId = str(proInfo[1])
        period = str(proInfo[2])
        state = str(proInfo[3])
        curs.execute('insert into tb_report(user_id,app_id,file_id,project_id,period,flag,state) values('+userId+','+appId+','+fileId+ ','+projectId+',' +period+',0,'+state+')')
        num += 1
print num
