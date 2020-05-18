# -*- coding: utf-8 -*-
# 功能：判断时间是否合法
# 返回：True or False

import re
import time
import datetime


# '2015-08-28 16:43:37.283' --> 1440751417.283
# 或者 '2015-08-28 16:43:37' --> 1440751417.0
def string2timestamp(strValue):
    """最高精度：毫秒"""
    try:
        d = datetime.datetime.strptime(strValue, "%Y-%m-%d %H:%M:%S.%f")
        t = d.timetuple()
        timeStamp = int(time.mktime(t))
        timeStamp = float(str(timeStamp) + str("%06d" % d.microsecond)) / 1000000
        # print timeStamp
        return timeStamp
    except ValueError as e:
        # print e
        d = datetime.datetime.strptime(strValue, "%Y-%m-%d %H:%M:%S")
        t = d.timetuple()
        timeStamp = int(time.mktime(t))
        timeStamp = float(str(timeStamp) + str("%06d" % d.microsecond)) / 1000000
        # print timeStamp
        return timeStamp

# 1440751417.283 --> '2015-08-28 16:43:37.283'
def timestamp2string(timeStamp):
    """最高精度：毫秒"""
    try:
        d = datetime.datetime.fromtimestamp(timeStamp)
        str1 = d.strftime("%Y-%m-%d %H:%M:%S.%f")
        # 2015-08-28 16:43:37.283000'
        return str1
    except Exception as e:
        print e
        return ''

def isvalidTime(timeStr):
    try:
        time.strptime(timeStr, "%Y-%m-%d %H:%M:%S")
        return True
    except:
        return False


# 功能：解析文件中的时间
# 返回：标准格式的时间
def timeParser(oldtimeline):
    """最高精度：秒"""
    timeline1 = oldtimeline.strip('\n')                 # 去掉换行符
    timeline = timeline1.replace("&nbsp;+", " ")        # 替换掉&nbsp;方便后续处理
    # print timeline

    # 2018-09-05 11:22:30 类型
    matchTime = re.search("[0-9]+-[0-9]+-[0-9]+ +[0-9]+:[0-9]+:[0-9]+", timeline)
    if matchTime:
        matchTime = matchTime.group()
        timeList = matchTime.split(' ')
        timeList1 = timeList[0].split('-')
        timeList2 = timeList[1].split(':')
        timeoutput = "%s-%s-%s %s:%s:%s" % (timeList1[0], timeList1[1], timeList1[2], timeList2[0], timeList2[1], timeList2[2])
        if not isvalidTime(timeoutput):
            return None
        return timeoutput

    # 2018/7/16 11:12:11 类型
    matchTime = re.search("[0-9]+/[0-9]+/[0-9]+ +[0-9]+:[0-9]+:[0-9]+", timeline)
    if matchTime:
        matchTime = matchTime.group()
        timeList = matchTime.split(' ')
        timeList1 = timeList[0].split('/')
        timeList2 = timeList[1].split(':')
        timeoutput = "%s-%s-%s %s:%s:%s" % (timeList1[0], timeList1[1], timeList1[2], timeList2[0], timeList2[1], timeList2[2])
        if not isvalidTime(timeoutput):
            return None
        return timeoutput

    # 2018/7/16 11:12 类型
    matchTime = re.search("[0-9]+/[0-9]+/[0-9]+ +[0-9]+:[0-9]+", timeline)
    if matchTime:
        matchTime = matchTime.group()
        timeList = matchTime.split(' ')
        timeList1 = timeList[0].split('/')
        timeList2 = timeList[1].split(':')
        timeoutput = "%s-%s-%s %s:%s:%s" % (timeList1[0], timeList1[1], timeList1[2], timeList2[0], timeList2[1], "00")
        if not isvalidTime(timeoutput):
            return None
        return timeoutput

    # 13:51:16 这种类型
    matchTime = re.findall("[0-9]+:[0-9]+:[0-9]+", timeline)
    if matchTime:
        timeList = matchTime[0].split(':')
        timeoutput = "%s-%s-%s %s:%s:%s" % ("1970", "01", "01", timeList[0], timeList[1], timeList[2])
        if not isvalidTime(timeoutput):
            return None
        return timeoutput

    # 11:30 这种类型
    matchTime = re.findall("[0-9]+:[0-9]+", timeline)
    if matchTime:
        timeList = matchTime[0].split(':')
        timeoutput = "%s-%s-%s %s:%s:%s" % ("1970", "01", "01", timeList[0], timeList[1], "00")
        if not isvalidTime(timeoutput):
            return None
        return timeoutput

    # 11点30分 这种类型
    matchTime = re.findall("[0-9]+点[0-9]+分", timeline)
    if matchTime:
        timeList = matchTime[0].split('点')
        timeList[1] = (timeList[1].split('分'))[0]
        timeoutput = "%s-%s-%s %s:%s:%s" % ("1970", "01", "01", timeList[0], timeList[1], "00")
        if not isvalidTime(timeoutput):
            return None
        return timeoutput

    # 2018-11-18-2-08-14-01-37 这种类型
    matchTime = re.findall("[0-9]+-[0-9]+-[0-9]+-[0-9]+-[0-9]+-[0-9]+", timeline)
    if matchTime:
        timeList = matchTime[0].split('-')
        timeoutput = "%s-%s-%s %s:%s:%s" % (timeList[0], timeList[1], timeList[2], timeList[3], timeList[4], timeList[5])
        if not isvalidTime(timeoutput):
            return None
        return timeoutput

    return None
