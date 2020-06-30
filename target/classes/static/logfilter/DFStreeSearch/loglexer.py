#!/usr/bin/python
# coding=utf-8
import codecs
from datetime import datetime
import re
import time

timestamp_regex = ".*?(\d\d-\d\d \d\d:\d\d:\d\d\.\d\d\d).*?(\d\d\d\d).*?(\d\d\d\d).*?[VIDWE] (.*)"
logline_regex = ".*?(\d\d-\d\d \d\d:\d\d:\d\d\.\d\d\d).*?(\d+).*?(\d+).*?[VIDWEF] (.*)"
time_regex = ".*?\d\d-\d\d (\d\d:\d\d:\d\d)\.(\d\d\d).*"
regex_for_log_line = ".*?(\d\d-\d\d \d\d:\d\d:\d\d)\.(\d\d\d).*"
begin_info = '--------- beginning of'



from abc import ABCMeta, abstractmethod
from readerlog import FileReader
from token import Token
from token import LogToken
from token import InfoToken

class Lexer(object):
    __metaclass__ = ABCMeta

    @abstractmethod
    def read(self):
        pass

    @abstractmethod
    def peek(self, i):
        pass


class BtLogLexer(Lexer):

    def __init__(self, f):
        self.reader = FileReader(f)
        self.queue = []
        self.first_log_time = None
        self.last_log_time = None

    def get_lexe_time_block(self):
        return (self.first_log_time,self.last_log_time)

    def read(self):
        if self.fill_queue(0):
            return self.queue.pop(0)
        return Token.EOF


    def peek(self, i):
        if self.fill_queue(i):
            return self.queue[i]
        return Token.EOF


    def fill_queue(self, i):
        while i >= len(self.queue):
            token = self.add_token()
            if token != Token.EOF:
                self.queue.append(token)
            else:
                return False
        return True


    def add_token(self):
        line_char = self.reader.read_line()
        line_char = line_char.strip()
        matchObj = re.match(logline_regex, line_char)
        if (matchObj):
            tag, content = matchObj.group(4).split(":", 1)
            logtime = matchObj.group(1)
            pid = matchObj.group(2)
            tid = matchObj.group(3)
            #print logtime, pid, tid, tag, content
            if self.first_log_time == None:
                self.first_log_time = logtime
            self.last_log_time = logtime
            return LogToken(self.reader.line_no,logtime,pid,tid,tag,content)
        elif begin_info in line_char:
            return InfoToken(self.reader.line_no, line_char)
        elif len(line_char)==0:
            return Token.END
        else:
            return Token.EOF





