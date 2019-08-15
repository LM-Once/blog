#! -*- utf-8 -*-

import re
import datetime

class Token(object):

    TOKEN_NUM = 0
    TOKEN_ID = 1
    TOKEN_STR = 2
    TOKEN_LOG = 3
    TOKEN_INFO = 4

    def __init__(self, line):
        self.line_number = line
        self.type = -1  

    def __repr__(self):
        return '<%s %s>' % (self.__class__.__name__, self.text())

    def text(self):
        return str(self.value)

    def get_line_number(self):
        return self.line_number

    def __str__(self):
        return str(self.value)
        

Token.EOF = Token(-1)
Token.END = Token(-2)
Token.EOL = "\n"

class LogToken(Token):

    def __init__(self,line,logtime,pid,tid,tag,content):
        super(LogToken, self).__init__(line)
        self.log_time = logtime
        self.log_pid = pid
        self.log_tid = tid
        self.log_tag = tag
        self.log_content = content
        self.type = Token.TOKEN_LOG
        pass

    def get_log_time(self):
        return self.log_time

    def get_log_pid(self):
        return self.log_pid

    def get_log_tid(self):
        return self.log_tid

    def get_log_tag(self):
        return self.log_tag

    def get_log_content(self):
        return self.log_content

    def count_time(self,token):
        d1 = datetime.datetime.strptime(self.log_time, '%m-%d %H:%M:%S.%f')
        d2 = datetime.datetime.strptime(token.get_log_time(), '%m-%d %H:%M:%S.%f')
        delta = d1 - d2
        return delta.total_seconds()*1000
        
    def getType(self):
        return self.type


class InfoToken(Token):

    def __init__(self, line, info):
        super(InfoToken, self).__init__(line)
        self.log_info = info
        self.type = Token.TOKEN_INFO

    def get_info(self):
        return self.log_info

    def getType(self):
        return self.type


class NumToken(Token):

    def __init__(self, line, v):
        super(NumToken, self).__init__(line)
        self.value = v
        self.type = Token.TOKEN_NUM

    def get_number(self):
        if re.match('\d+', str(self.value)):
            return int(self.value)
        elif re.match('^\d+\.\d+$', str(self.value)):
            return float(self.value)
    
    def getType(self):
        return self.type


class IdToken(Token):

    def __init__(self, line, i):
        super(IdToken, self).__init__(line)
        self.value = i
        self.type = Token.TOKEN_ID

    def getType(self):
        return self.type


class StrToken(Token):

    def __init__(self, line, i):
        super(StrToken, self).__init__(line)
        self.value = i
        self.type = Token.TOKEN_STR

    def getType(self):
        return self.type
