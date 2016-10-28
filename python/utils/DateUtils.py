#!/usr/bin/python
# -*- coding: utf-8 -*-
# python version 2.7.6

__des__='时间转换工具类'
__author__='lin'

# 还是有缺陷的，需要改进
# 1.输入类型应当考虑：字符串、datetime、时间戳
# 2.输入格式应当不限于：%Y-%m-%d %H:%M:%S
# 3.类型／格式错误的提示

import time,datetime

# 字符串转 datetime  
def string_toDatetime(string):  
    return datetime.datetime.strptime(string, "%Y-%m-%d %H:%M:%S")

# datetime 转字符串  
def datetime_toString(dt):  
    return dt.strftime("%Y-%m-%d %H:%M:%S")

# 字符串转时间戳  
def string_toTimestamp(strTime):  
    return time.mktime(string_toDatetime(strTime).timetuple())

# 时间戳转字符串
def timestamp_toString(stamp):
    return time.strftime("%Y-%m-%d %H:%M:%S", time.localtime(stamp))

# datetime 转时间戳  
def datetime_toTimestamp(dateTim):  
    return time.mktime(dateTim.timetuple())

# 时间戳转 datetime
def timestamp_toDatetime(stamp):  
    return string_toDatetime(timestamp_toString(stamp))

# UTC 转本地时间（东8区，北京时间）
# 输入格式为(datetime)：2015-08-19 07:11:31
# 输出格式为(datetime)：2015-08-19 15:11:31
def utc2local(utctime):
    utc_long = datetime_toTimestamp(utctime)
    local_long = utc_long - time.timezone
    return timestamp_toDatetime(local_long)

# 本地时间（东8区，北京时间）转 UTC
# 输入格式为(datetime)：2015-08-19 15:11:31
# 输出格式为(datetime)：2015-08-19 07:11:31
def local2utc(localtime):
    local_long = datetime_toTimestamp(localtime)
    utc_long = local_long + time.timezone
    return timestamp_toDatetime(utc_long)


if __name__ == '__main__':
    st = '2015-08-19 15:11:31'

    dt1 = string_toDatetime(st)
    tt1 = string_toTimestamp(st)

    st1 = datetime_toString(dt1)
    tt2 = datetime_toTimestamp(dt1)
    
    st2 = timestamp_toString(tt1)
    dt2 = timestamp_toDatetime(tt1)

    dt3 = local2utc(utc2local(dt1))
    dt4 = utc2local(local2utc(dt2))

    print st1 == st2 == st
    print dt1 == dt2 == dt3 == dt4
    print tt1 == tt2
