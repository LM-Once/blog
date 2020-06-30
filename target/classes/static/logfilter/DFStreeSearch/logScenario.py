# -*- coding: utf-8 -*-
# File:   
# Author: 
# Date:   
# Modified: 
# Date:   

"""
Created on Thu Jul 11 09:27:04 2019

@author: 莫楚轩 S9023069
"""
#解析失败；

import sys
import time
import re
import json
from timeParser import *

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

def p_debug(string):
    print "*********\n" 
    print string 
    print "**********\n"

class Node(object):
    """ An node base class. Timestamp precision: milisecond"""
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
        if type(timestamp) == type(1) or type(timestamp) == type(1.0):
            self.timestamp = float(timestamp)
            if timestamp > 10000000:
                self.print_time = timestamp2string(timestamp)
            else:
                self.print_time = str(0.0)
        else:
            str_time = year + "-" + timestamp
            self.print_time = str_time
            self.timestamp  = string2timestamp(str_time)



    def get_id(self):
        return self.ID
    
    def get_attr(self):
        return self.attr
        
    def get_type(self):
        return self.attr
    
    def get_timestamp(self):
        return self.timestamp
    
    def set_timestamp(self, timestamp):
        self.timestamp = float(timestamp)


class TimeNode(Node):
    """ A time node"""
    def __init__(self, ID, attr, duration, timestamp):
        super(TimeNode, self).__init__(ID, attr, Node.NULL_PRIORITY, timestamp)
        self.duration = float(duration)
        
    def __repr__(self):
        return ("time node(ID: %s, timestamp: %s, duration: %s) children: %d parents: %d"
                % (self.ID, self.print_time, self.duration, len(self.chlds), len(self.parents)))
        
    def get_duration(self):
        return self.duration

    def to_dict(self):
        msg = {}
        msg["ID"] = self.ID
        msg["attr"] = self.attr
        msg["timestamp"] = self.timestamp
        msg["duration"] = self.duration
        msg["children_number"] = len(self.chlds)
        msg["parents_number"] = len(self.parents)
        return msg


class GeneralNode(Node):
    """A LogScenario general node."""
    def __init__(self, ID, attr, tag, timestamp, priority, content):
        super(GeneralNode, self).__init__(ID, attr, priority, timestamp)
        self.tag = tag
        self.content = content
        
    def __repr__(self):
        if self.attr == Node.TIME: str_attr = "time"
        elif self.attr == Node.ERROR: str_attr = "error"
        elif self.attr == Node.LOG: str_attr = "LOG"
        return ("%s node(ID: %s, timestamp: %s, tag: %s, priority: %d, content: %s)"
                "children: %d, parents: %d" % (str_attr, self.ID, self.print_time, self.tag,
                                  self.priority, self.content, len(self.chlds), len(self.parents)))
    
    def __eq__(self, other):
        return (self.tag == other.tag) and (self.content == other.content)
    
    def __ne__(self, other):
        return (self.tag != other.tag) or (self.content != other.content)
    
    def to_dict(self):
        msg = {}
        msg["ID"] = self.ID
        msg["attr"] = self.attr
        msg["tag"] = self.tag
        msg["timestamp"] = self.timestamp
        msg["priority"] = self.priority
        msg["content"] = self.content
        msg["children_number"] = len(self.chlds)
        msg["parents_number"] = len(self.parents)
        return msg
    
    def get_tag(self):
        return self.tag
    
    def get_priority(self):
        return self.priority    

class Link:
    """A directed edge linking two LogScenario nodes."""
    
    def __init__(self, ID, begin, end):
        self.ID = ID
        self.begin = begin
        self.end = end
        
    def __repr__(self):
        return "Link(%s, %s)" % (self.begin, self.end)
    
    def to_dict(self):
        msg = {}
        msg["ID"] = self.ID
        msg["begin"] = self.begin.ID
        msg["end"] = self.end.ID
        return msg

    
class Loader:
    """
    An instance of Loader can be used to access LogScenario's node and link
    """
    def __init__(self, filename):
        node_for_id = {}      # node ID -> node map
        link_for_begin = {}   # begin node -> link
        
        self._nodes = []
        self._links = []
        try:
            src_file = open(filename, 'r')
            line = src_file.next()
            while not re.search("\[nodes\]", line):
                line = src_file.next()

            # parse node
            print "nodes parsing beginning"
            while not re.search("/nodes", line):
                if not re.search("\[tu\]", line):
                    line = src_file.next()
                    continue
                str_attr, line = self._get_val(src_file, line, "attr")
                if str_attr == "time":
                    ID, line = self._get_val(src_file, line, "id")
                    label, line = self._get_val(src_file, line, "label")
                    duration = float(label[:len(label)-1])
                    node = TimeNode(ID, Node.TIME, duration, Node.NULL_TIMESTAMP)
                else:
                    attr = Node.LOG if str_attr=="log" else Node.ERROR
                    ID, line = self._get_val(src_file, line, "id")
                    content, line = self._get_val(src_file, line, "content")
                    priority, line = self._get_val(src_file, line, "priority")
                    priority = int(priority)
                    tag, line = self._get_val(src_file, line, "label")
                    node = GeneralNode(ID, attr, tag.strip(), Node.NULL_TIMESTAMP,
                                       priority, content.strip())
                node_for_id[ID] = node
                self._nodes.append(node)
            print "nodes parsing finished"

            # parse link
            print "links parsing beginning"
            while not re.search("\[edges\]", line):
                line = src_file.next()
            while not re.search("/edges", line):
                if not re.search("\[tu\]", line):
                    line = src_file.next()
                    continue
                begin, line = self._get_val(src_file, line, "source")
                end, line = self._get_val(src_file, line, "target")
                ID, line = self._get_val(src_file, line, "id")
                link = Link(ID, node_for_id[begin], node_for_id[end])
                link_for_begin[begin] = []
                link_for_begin[begin].append(link)
                self._links.append(link)
                continue
            print "links parsing finished"
        except Exception, e:
            print e
        finally:
            src_file.close()
    
    def _get_val(self, src_file, line, key):
#      try:
        while not re.search(key, line):
            line = src_file.next()
            begin, end = re.search(r':(.*)]', line).span()
#        except Exception, e:
#            print e
        return line[begin+1:end-1], line

    def get_nodes(self):
        return self._nodes
    
    def get_links(self):
        return self._links
                 
         
class LogScenario:
    def __init__(self, filename):
        """ creates a log sub tree with nodes, links. """
        self.filename = filename
        self.nodes, self.links = self._load_data()
        self._create_heterogeneous_graph()
        self.root = self._find_root()
        self.tot_duration = float(self._dfs_tot_duraiton())
        
        
    def __str__(self):
        """String representation of the LogScenario size"""
        num_nodes = len(self.nodes)
        num_edges = len(self.links)
        return "Graph size: %d nodes, %d edges" % (num_nodes, num_edges)
    
    def _load_data(self):
        loader = Loader(self.filename)
        lnodes = loader.get_nodes()
        llinks = loader.get_links()
        return lnodes, llinks
    
    def _create_heterogeneous_graph(self):
        """
        First creates a original heterogeneous graph based on the node set and 
        link set, then convert it to homogenous graph based on the heterogeneous one.
        """
        for link in self.links:
#            print link
            link.begin.chlds.append(link.end)
            link.end.parents.append(link.begin)
        for node in self.nodes:
            node.chlds.sort(lambda n1, n2: n2.priority - n1.priority) 
#            print 'node: ', node, '\nnode.chlds:', node.chlds
    
    def _find_root(self):
        """find the root for the log scenario"""
        node = self.nodes[0]
        while node.parents:
            node = node.parents[0]
        return node
        
    def get_root(self):
        return self.root
    
    def _dfs_tot_duraiton(self):
        return 10.0
    
    def get_tot_duration(self):
        return self.tot_duration

    def get_diff(self, other_nodes=[]):
        """本图中节点集合为A，other_nodes集合为B，返回结果为A-B，集合均为id集合"""
        B = set()
        res = []
        for n in other_nodes:
            B.add(n.ID)
        for n in self.nodes:
            if n.ID not in B: res.append(n)
        return res



