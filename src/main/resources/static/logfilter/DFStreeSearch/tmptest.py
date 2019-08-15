# -*- coding: utf-8 -*-
# File:   
# Author: 
# Date:   
# Modified: 
# Date:   

"""
Created on Thu Jul 11 09:27:04 2019

@author: mochuxuan
"""

"""
定义target图文件对应的类LogScenario中的node.

LogScenario的异常处理：一个log节点指向time节点后，time节点不再指向其他节点
"""

import sys
import time
import re

# edge constant
# offset in the *.tu file
SOURCE = 0
TARGET = 1


# node constant
# offset int the *.tu file 
ATTR = 0
ID = 1
LABEL = 4
CONTENT = 7
TAG = 13

class Node(object):
    """ An node base class"""
    # node attr
    TIME = 0
    LOG = 1
    ERROR = 2
    NULL_ATTR = -1
    NULL_TIMESTAMP = -1
    NULL_PRIORITY = -1

    def __init__(self, ID, attr, priority, timestamp):
        self.ID = ID
        self.attr = attr
        self.priority = priority
        self.parents = []
        self.chlds = []
        year = str(2019)
        if type(timestamp) == type(1): self.timestamp = timestamp
        else:
            tmpTimestamp = year + "-" + timestamp
            tmpTimestamp = tmpTimestamp[:-4]
#            print "********\n"
#            print tmpTimestamp
#            print "********\n"
            self.timestamp = time.mktime(
                    time.strptime(tmpTimestamp,"%Y-%m-%d %H:%M:%S"))
        
    def getID(self):
        return self.ID
    
    def getAttr(self):
        return self.attr
        
    def getType(self):
        return self.attr
    
    def setTimestamp(self, timestamp):
        self.timestamp = timestamp


class TimeNode(Node):
    """ An time node"""
    def __init__(self, ID, attr, duration, timestamp):
        super(TimeNode, self).__init__(ID, attr, Node.NULL_PRIORITY, timestamp)
        self.duration = duration
        
    def __repr__(self):
        return "time node(ID: %s, duration: %s)" % (self.ID, self.duration)
        
    def getDuration(self):
        return self.duration

class GeneralNode(Node):
    """An LogScenario general node.
    timestamp应该换成格林时间来存才对"""
    def __init__(self, ID, attr, tag, timestamp, priority, content):
        super(GeneralNode, self).__init__(ID, attr, priority, timestamp)
        self.tag = tag
        self.content = content
        
    def __str__(self):
#        strAttr = "None"
        if self.attr == Node.TIME: strAttr = "time"
        elif self.attr == Node.ERROR: strAttr = "error"
        elif self.attr == Node.LOG: strAttr = "LOG"
#        print strAttr
        return ("%s node(ID: %s, timestamp: %d, tag: %s, priority: %d, content: %s) children: %s"
                % (strAttr, self.ID, self.timestamp, self.tag, self.priority, 
                   self.content, str(self.chlds)))
    
    def __eq__(self, other):
        return (self.tag == other.tag) and (self.content == other.content)
    
#    def __ne__(self, other):
#        return (self.tag != other.tag) or (self.content != other.content)
    
    def getTag(self):
        return self.tag
    
    def getpriority(self):
        return self.priority
        
#    def addTimestamp(self, parent, timestamp):
#        self.timestamps[parent] = timestamp

class Link:
    """A directed edge linking two LogScenario nodes."""
    
    def __init__(self, ID, begin, end):
        self.ID = ID
        self.begin = begin
        self.end = end
        
    def __repr__(self):
        return "Link(%s, %s)" % (self.begin, self.end)