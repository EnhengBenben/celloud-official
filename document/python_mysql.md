#使用python操作mysql数据库

```

 #encoding=utf-8
 #coding=utf-8

 #导入MySQLdb模块，注意大小写（如果是没有安装，ubuntu->sudo apt-install python-mysqldb）
import sys
import MySQLdb

try:
	# Python2.5 初始化后会删除 sys.setdefaultencoding 这个方法，我们需要重新载入	
	reload(sys)
	#为了解决乱码问题
	sys.setdefaultencoding('utf-8')	
	#建立和数据库的连接
	conn = MySQLdb.connect(host='localhost',user='root',passwd='passwd',charset='utf8')
	#选择数据库
	conn.select_db('celloud')
	#获取操作游标
	cur = conn.cursor()
	#执行SQL
	#cur.execute('create database if not exists python character set utf8')


	#建表
	#sql_create_user = 'create table tbl_user(id int unsigned primary key auto_increment, username varchar(20) not null,sex char(2) not null) default charset=utf8'
	#cur.execute(sql_create_user)
	#插入记录
	#sql_insert_user = "insert into tbl_user(username,sex) values('方平','女')"
	#cur.execute(sql_insert_user)

	#查询记录
	sql_select_user = 'select * from tb_company'
	cur.execute(sql_select_user)
	result = cur.fetchone()
	#获取第一条记录
	print '第一条记录是：'
	#为了解决乱码问题
	result = str(result)
	result = result.decode('utf-8')
	print result

	#提交事物（开始死活插不进去，使劲插，各种纠结，也不报错，就是少了这句，有些版本的驱动可能不需要）
	#conn.commit()
	#关闭连接，释放资源
	cur.close()
	conn.close()
except MySQLdb.Error,e:
     print "Mysql Error %d: %s" % (e.args[0], e.args[1])

```
