# -*- coding: utf-8 -*-
# File:   
# Author: 莫楚轩
# Date:   
# Modified: 
# Date:  
"""
Created on Thu Jul 11 15:49:45 2019

@author: 莫楚轩 S9023069
"""
import sys
import os
import time
import codecs
import copy

from loglexer import BtLogLexer
from token import Token
from logScenario import *
from timeParser import *
from Queue import *
from collections import deque

FIRST_DAY= 57599.0

RES_LOG_TIME_NOT_MATCH = 0
RES_LOG_TIME_ILLEGAL = 1
RES_LEGAL_PATH = 2
RES_HIT_ERROR_NODE = 3
RES_INCOMPLETE = 4 #

WINDOW_DURATION = 300

SINGLE_PATH_SEARCH = True
FULL_GRAPH_SEARCH = False

"""
Token:
    
    def get_log_time(self):
        return self.log_time
    
    def get_log_pid(self):
        return self.log_pid
        
    def get_log_tid(self):
        return self.log_tid
        
    def get_log_content(self):
        return log_content
        
    def count_time(self, token):
        d1 = datetime.datetime.strptime(self.log_time, '%m')
"""



def time_diff(node1, node2):
    """Returns the time difference of two nodes in a particular path presented 
    by stack 
    """
    res = node1.timestamp - node2.timestamp
    return res

def match(context, pattern):
    return (context.tag == pattern.tag) and (pattern.content in context.content)


class HasPushNetPath(object):
    def __init__(self, flag):
        self.flag = flag

class LogNetwork(object):
    """日志网络图，从日志记录文件创建，提供一定时间跨度的序列节点流输出"""
    
    def __init__(self, filename, tot_duration=WINDOW_DURATION):
        """构造函数
        
        参数：
            filename: 待加载的日志记录文件
            tot_duration：时间窗口的时间跨度
        """
        self.filename = filename
        self._bt_log_lexer = None
        self.tot_duration = float(tot_duration)
        self.window = deque() 
        # 该窗口有点奇怪，移动时是一个一个log token的移动，大小则由duration来决定
        self._load_file(filename)
        self._buff = None
        self.tmp_id = 0
        self.base_time = 57599.0 # 1970-01-01 23:59:59
        
        self.tot_cnt = 0
        
        self.locate_flag = False
        self.time_hole_flag = False #遇到时间空洞，由_filler置位()，locate_win()来清零
        
    def __str__(self):
        return ("filename: %s; window size: %d second, %d records" 
                % (self.filename, self.tot_duration, len(self.window)))
        
    def _load_file(self, filename):
        f = codecs.open(filename, "r" , None)
        self._bt_log_lexer = BtLogLexer(f)
            
#-------- 3 2 1
#---------- 5 4 3 2 5
#----------- 5
    def _get_from_buffer(self):
        """从_bt_log_lexer到window的中间缓冲区"""
        if self._buff: 
            return self._buff
        else: 
            token = self._bt_log_lexer.read()
            self.tot_cnt  += 1
            if token != Token.END:  
                self._buff = token
                return self._buff
            else: 
                return None
    
    def _pop_buffer(self):
        self._buff = None
    
    def _filler(self):
        if not self.window: 
            start_ind = 0 # 记录window新添加的分界点
            self.time_hole_flag = True # 遇到时间空洞
        else: 
            start_ind = len(self.window) - 1
        token = self._get_from_buffer()
        while token != None: # not until EOF 
            if token.type == Token.TOKEN_LOG:
                self.tmp_id += 1
                node = GeneralNode(self.tmp_id, Node.LOG, token.get_log_tag(), 
                                   token.get_log_time(), Node.NULL_PRIORITY, 
                                   token.get_log_content().strip())
                if (len(self.window) >= 1 and
                    time_diff(node, self.window[0]) > self.tot_duration):
                    break
                self.window.append(node)
                self._pop_buffer()
            # 维持tot_duration时间窗口的节点
            else: # 弹出非 log token
                self._pop_buffer()
            token = self._get_from_buffer()            
        if self.window: 
            self._create_adjacency_lists(start_ind, len(self.window)-1) 
            # 在窗口变成3 2 1 0，即最后一次时，end_ind = -1
        
    def get_start(self):
        return self.window[0] if self.window else None
    
    def get_end(self):
        return self.window[-1] if self.window else None
        
    def _create_adjacency_lists(self, start_ind, end_ind):
        """以start_ind, end_ind作为首尾的index来将窗口里的节点连成链表"""
        for i in range(start_ind, end_ind):
            self.window[i].next = self.window[i+1]
        self.window[end_ind].next = None
                
    def roll(self):
        """每次调用输出日志记录中的下一个节点，同时维护内存中特定时间跨度的节点缓冲区，直到EOF
        
        返回：
            若到达文件尾，则返回None；否则返回窗口中的首节点
        """
        if self.locate_flag: 
            self.locate_flag = False
            return self.get_start()
        if self.window: self.window.popleft()
        self._filler()
        return self.get_start()
        
    def _next_end(self):
        """窗口滚动过程中返回窗口的尾节点"""
        if self.window: self.window.popleft()
        self._filler()
        return self.get_end()
        
    def locate_win(self, log_time):
        """将窗口的尾部定位到给定的时刻
        
        参数：
            log_time：格式为格林时间的秒数
        
        返回：
            True定位成功，False定位失败
         
        采用二分搜索进行优化
        
        window的特殊情况：time hole, t2-t1 < duration, t4-t3<duration, t3-t2>duration
        另外locate_win，当遇到EOF即可停止，不需要项roll一样继续读，因为roll从窗口头开始读，这里是尾
        """
        if (not log_time) or (log_time < 31593599.0): return False
        node = self._next_end()
#         locate_flag标志已调用该函数并进行窗口初始化工作，
#        接着调用roll时不能popleft，避免走漏第一个节点
        self.locate_flag = True 
        while node:
            # log_time可能出现在time hole之后重新建立起来的窗口的非尾部
            if self.time_hole_flag:
                for n in self.window:
                    if int(n.get_timestamp()) == log_time: return True
                self.time_hole_flag = False            
            if int(node.get_timestamp()) == log_time: return True
            node = self._next_end()
        return False
        
    def get_base_time(self):
        return self.base_time
    
class LogSceFinder(object):
    """日志搜索器，由日志网络、日志场景图、目标日志时刻创建
    """
    
    def __init__(self, network, scenario, log_time, config):
        """

        :param network:
        :param scenario:
        :param log_time: (start_time, end_time) 时间格式为格林时间的秒数，精度毫秒
        :param config:
        """
        self.network = network
        self.scenario = scenario
        self.log_time = log_time
        self.config = config
        
        self.legal_nodes = set()  
        # 记录符合整个合法scenario的node，寻找的时候跳过这样的node
        
        self.sce_path = []  # 记录sce图的dfs路径，用于实时生成时间戳
        self.net_path = [] # dfs时，隐式日志网络图回溯时用
        
        self.err_nodes = []
        self.nodeRes = []
        
        self.errFound = False
        self.pathFound = False
        self.nothingFound = False
        self.parentNum = {}  # node->#(parents), dfs时确保多父的节点被访问的次数正确

    def find(self):
        """搜索场景图在日志记录中出现的情况
        """  
        res = []
        rootSce = self.scenario.get_root()
        rootNet = self.network.roll()
        while rootNet and rootNet.timestamp <= self.log_time[1]:
            hasPushNetPath = HasPushNetPath(False)
            self.errFound = False
            self.pathFound = False
            self.nothingFound = False
            self.sce_path.append(rootSce)
#            print '*************\n','rootSce: ',rootSce, '\n', 'rootNet: ', rootNet, '\n**********\n'
            findFlag = self._dfs(rootNet, rootSce, hasPushNetPath, self.config["search_pattern"])
            self.sce_path.pop()
#            print "findflag", findFlag
            if findFlag:
                if self.errFound:
                    res.append(SceRes(self.log_time, RES_HIT_ERROR_NODE, self.nodeRes,
                                      self.scenario.get_diff(self.nodeRes), self.err_nodes))
                elif self.pathFound:
                    res.append(SceRes(self.log_time, RES_LEGAL_PATH, self.nodeRes, self.scenario.get_diff(self.nodeRes)))
            else:
                if self.nothingFound: 
                    pass
                else: 
                    res.append(SceRes(self.log_time, RES_INCOMPLETE, self.nodeRes, self.scenario.get_diff(self.nodeRes)))
            self.nodeRes = []
            if hasPushNetPath.flag: self.net_path.pop()
            rootNet = self.network.roll()
        if not res: res.append(SceRes(self.log_time, RES_INCOMPLETE, self.nodeRes))
        return LogResults(res)
        
    def _dfs(self, rootNet, rootSce, hasPushNetPath, trivial_search=False):
        """比较以rootSce为根节点的图与以rootNet为根节点的隐式图是否相符
        
        参数：
            rootNet：场景图的根节点
            rootSce：日志隐式图的根节点
            hasPutNetPath：值-结果参数，来标记当前dfs是否将rootNet压栈
            trivial_search：True代表简单搜索，遇到一条合法路径及第一个错误节点即返回
        
        返回值：
            True: 遇到errorNode或找到一条合法路径；
            False：找不到一条合法路径
        """
        if not self.is_legal(rootNet, rootSce, hasPushNetPath): return False
        if rootSce.get_attr() == Node.ERROR:    # 遇到错误节点
        ### >>>
            self.net_path[-1].ID = rootSce.ID
            self.err_nodes.append(self.net_path[-1])
            self.errFound = True
            return True
        ###
#            rootNet.ID = rootSce.ID
#            self.err_nodes.append(rootNet)
#            self.errFound = True
#            return True
        ### <<<
        if not rootSce.chlds:  # 遇到一个合法的叶子节点
            self.pathFound = True
            return True
        
        flag = False
        for child in rootSce.chlds:
            if (child in self.parentNum) and (self.parentNum[child] <= 0): # 对该child的访问已结束    
                continue
            if child not in self.parentNum:  # 从未访问过chlid
                self.parentNum[child] = len(child.parents)
            # 以下未访问完child（针对于多父的情形）或者从未访问过，
            self.parentNum[child] -= 1
            self.sce_path.append(child)    # 压栈
            subHasPushNetPath = HasPushNetPath(False)
            ### >>>            
#            print 'rootSce: ', self.net_path, '\nchild: ', child
            flag = (self._dfs(self.net_path[-1].next, child, subHasPushNetPath, trivial_search) 
            or flag) # 避免短路特性
            self.sce_path.pop()      # 弹出
#            print 'rootSce: ', self.net_path, '\nchild: ', child
            
#            print "trivial_search:", trivial_search, "flag: ", flag, "\n"
            if trivial_search and flag: 
#                print "child: ", child, "\n"
                return True
            if subHasPushNetPath.flag: self.net_path.pop() 
        return flag
            
            ###
#            if self._dfs(self.net_path[-1].next, child, subHasPushNetPath):
#                return True # 找到一条路径或者遇到errorNode，结束搜索
#            self.sce_path.pop()      # 弹出
#            if subHasPushNetPath.flag: self.net_path.pop() 
#        return False # rootSce下所有的子节点均找不到
            ### <<<
            
    def is_legal(self, rootNet, rootSce, hasPushNetPath):
        """在network中以rootNet为根节点，在时间约束条件下判断是否存在后代与rootSce相同
        同时设置时间戳
        """
        node = rootNet
#        print '*************\n','rootSce: ',rootSce, '\n', 'rootNet: ', rootNet, '\n**********\n'  
        # 根节点没有时间约束
        if not rootSce.parents:
            if match(rootSce, node):
#                p_debug(node)
                node.ID = rootSce.ID
                self.net_path.append(node)
                hasPushNetPath.flag = True
                self.nodeRes.append(node)
                self.sce_path[0].set_timestamp(0)
                self.sce_path[0].time_delay =  (self.config["default_time_delay"] if self.config["default_time_delay"] > 0
                                                else self.config["time_window_size"])
                return True
            else: 
                self.nothingFound = True
                return False
        # 时间节点：时间戳定义为, timeNode(last node timestamp, time_delay)；直接合法
        if rootSce.attr == Node.TIME:
            self.sce_path[-1].set_timestamp(self.sce_path[-2].timestamp)
            self.sce_path[-1].time_delay = self.sce_path[-2].time_delay + rootSce.get_duration()
            self.nodeRes.append(rootSce)
            return True

        # 若是“结束的错误节点”则合法，否则当做正常节点去判断是否合法
        # if rootSce == Node.ERROR:
        #     if len(rootSce.content) == 0: return True;

        # 普通log节点, generalNode(last node timestamp + last node time_delay, 0), 例如有 generalNode(0, 0) -> timeNode(0, 5) -> generalNode(5, 0) -> generalNode(6, 0) -> timeNode(6, 5) -> timeNode(6, 10) -> generalNode(16, 0)
        if self.sce_path[-2].time_delay: 
            self.sce_path[-1].set_timestamp(
                    self.sce_path[-2].timestamp + self.sce_path[-2].time_delay)
        else: 
            self.sce_path[-1].set_timestamp(
                self.sce_path[-2].timestamp + 1)
        self.sce_path[-1].time_delay =  (self.config["default_time_delay"] if self.config["default_time_delay"] > 0
                                         else self.config["time_window_size"])
        while (node and 
               (time_diff(node, self.net_path[-1]) 
               <= time_diff(self.sce_path[-1], self.sce_path[-2]))):
            if match(rootSce, node):    # tag 和 content 相符
#                print '*************\n','rootSce: ',rootSce, '\n', 'node: ', node, '\n**********\n'
                node.ID = rootSce.ID
                self.net_path.append(node)
                self.nodeRes.append(node)
                hasPushNetPath.flag = True
                node.parents.append(self.net_path[-2])
                self.net_path[-2].chlds.append(node)
                return True
            else: 
                node = node.next
        return False

class SceRes(object):
    """一个场景图自根节点符合后的搜索结果"""
    
    def __init__(self, log_time, res_type, nodes_found=[], nodes_not_found=[], err_nodes=[]):
        """构造函数
        
        参数：
            log_time：待搜索场景对应的bug的时刻
            res_type：结果类型，包括bug的时刻错误（log_time格式非法RES_LEGAL_PATH
                或者不对应日志中任何时间点RES_LOG_TIME_NOT_MATCH）
                至少找到一条合法路径RES_LEGAL_PATH，找到错误节点RES_HIT_ERROR_NODE，
                根节点符合但其他均不符合RES_INCOMPLETE
            nodes_found：场景图中在日志文件能匹配上的节点
            nodes_not_found: 场景图中在日志文件匹配不到的节点
            err_nodes：错误节点信息
        """
        self.res_type = res_type
        self.log_time = log_time
        self.nodes_found = nodes_found
        self.nodes_not_found = nodes_not_found
        self.err_nodes = err_nodes
        if self.nodes_found: self.root = self.nodes_found[0]
        self.links = []
        if self.nodes_found: self._create_links()
        self.msg = {}
        
    def __str__(self):
        str_node = ""
        str_link = ""
        for n in self.nodes_found: str_node = (str_node + str(n) + "\n")
        for l in self.links: str_link = str_link + str(l) + "\n"
        return ("%d nodes, %d links\n"
                "\nnodes: \n%s"
                "\nlinks: \n%s"% 
                (len(self.nodes_found), len(self.links), str_node, str_link))
        
    def _create_links(self):
        i = 0
        for node in self.nodes_found:
            for child in node.chlds:
                self.links.append(Link(i, node, child))
                i += 1
    
    def get_msg(self):
        return self.msg
    
    def to_json(self):
        """返回该结果的json格式，如下
[
    {
        "Links":[

        ],
        "Nodes found":[
            {
                "content":"state:AUDIO_STATE_STARTED",
                "children_number":0,
                "attr":1,
                "timestamp":1564986623.483,
                "parents_number":0,
                "priority":-1,
                "tag":"A2dpStateMachine",
                "ID":"35049ea0"
            }
        ],
        "Result":"incomlete error",
        "LogTime":"from 2019-08-05 14:30:12 to 2019-08-05 15:33:20",
        "Nodes not found":[
            {
                "content":"Enter Disconnected",
                "children_number":1,
                "attr":1,
                "timestamp":86400,
                "parents_number":1,
                "priority":1,
                "tag":"A2dpStateMachine",
                "ID":"7f6f96d0"
            },
            {
                "content":"invokeEnterMethods: Disconnected",
                "children_number":1,
                "attr":1,
                "timestamp":-1,
                "parents_number":1,
                "priority":1,
                "tag":"HeadsetStateMachine",
                "ID":"fa762e93"
            },
            {
                "content":"MESSAGE_DEVICE_ACL_DISCONNECTED",
                "children_number":0,
                "attr":1,
                "timestamp":-1,
                "parents_number":1,
                "priority":1,
                "tag":"BluetoothActiveDeviceManager",
                "ID":"6ee24510"
            }
        ],
        "Class":"Cannot find a legal path or error node, meybe root cannot be found or the children of root cannot be found"
    },
    {
        "Links":[

        ],
        "Nodes found":[
            {
                "content":"state:AUDIO_STATE_STARTED",
                "children_number":0,
                "attr":1,
                "timestamp":1564986802.551,
                "parents_number":0,
                "priority":-1,
                "tag":"A2dpStateMachine",
                "ID":"35049ea0"
            }
        ],
        "Result":"incomlete error",
        "LogTime":"from 2019-08-05 14:30:12 to 2019-08-05 15:33:20",
        "Nodes not found":[
            {
                "content":"Enter Disconnected",
                "children_number":1,
                "attr":1,
                "timestamp":86400,
                "parents_number":1,
                "priority":1,
                "tag":"A2dpStateMachine",
                "ID":"7f6f96d0"
            },
            {
                "content":"invokeEnterMethods: Disconnected",
                "children_number":1,
                "attr":1,
                "timestamp":-1,
                "parents_number":1,
                "priority":1,
                "tag":"HeadsetStateMachine",
                "ID":"fa762e93"
            },
            {
                "content":"MESSAGE_DEVICE_ACL_DISCONNECTED",
                "children_number":0,
                "attr":1,
                "timestamp":-1,
                "parents_number":1,
                "priority":1,
                "tag":"BluetoothActiveDeviceManager",
                "ID":"6ee24510"
            }
        ],
        "Class":"Cannot find a legal path or error node, meybe root cannot be found or the children of root cannot be found"
    }
]
        """
        import json
        class_msg = None
        if not self.nodes_found and self.res_type != RES_INCOMPLETE:    # 搜索失败
            result_msg = "time error"
            if self.res_type == RES_LOG_TIME_ILLEGAL: 
                class_msg = "Log time is illegal"
            elif self.res_type == RES_LOG_TIME_NOT_MATCH: 
                class_msg = ("does not match any timestamp in log file, "
                             "maybe you should input date of format \"year-month-day hour:minute:second\"")
            self.msg["Result"] = result_msg
            self.msg["Class"] = class_msg
            self.msg["LogTime"] = ("from " + time.strftime("%Y-%m-%d %H:%M:%S", time.localtime(self.log_time[0])) +
                                   " to " + time.strftime("%Y-%m-%d %H:%M:%S", time.localtime(self.log_time[1])))
            self.msg["Nodes found"] = str(None)
            self.msg["Links"] = str(None)
        else:
            self.msg["LogTime"] = ("from " + time.strftime("%Y-%m-%d %H:%M:%S", time.localtime(self.log_time[0])) +
                                   " to " + time.strftime("%Y-%m-%d %H:%M:%S", time.localtime(self.log_time[1])))
            nodes_found = [n.to_dict() for n in self.nodes_found]
            nodes_not_found = [n.to_dict() for n in self.nodes_not_found]
            links = [l.to_dict() for l in self.links]
            self.msg["Nodes found"] = nodes_found
            self.msg["Nodes not found"] = nodes_not_found
            self.msg["Links"] = links
            if self.res_type == RES_HIT_ERROR_NODE:
                result_msg = "hit error node"
                class_msg = "Found a error node"
                err_nodes = [n.to_dict() for n in self.err_nodes]
                self.msg["Error nodes"] = err_nodes
            elif self.res_type == RES_LEGAL_PATH:

                result_msg = "Succeeded"
                class_msg = "Found a legal path"
            elif self.res_type == RES_INCOMPLETE:
                result_msg = "incomlete error"
                class_msg = "Cannot find a legal path or error node, meybe root cannot be found or the children of root cannot be found"
            self.msg["Result"] = result_msg
            self.msg["Class"] = class_msg
        return json.dumps(self.msg)
        


class LogResults(object):
    """日志搜索结果，由LogSceFinder.find()返回，包括多个SceRes结果"""
    
    def __init__(self, res=[]):
        self.res = res
    
    def print_legal_res(self): 
        """打印出符合日志场景的合法日志记录"""
        print "\n\nlegal results:"
        for r in self.res:
            print r  
            print "\n"
            
    def get_msg(self):
        """返回json子串格式的三种搜索结果列表，格式见本文末"""
        r = []
        if self.res:
            for i in self.res: r.append(i.to_json())
        return r
def log_scenario_test(sce):
    scenario = LogScenario(sce)
    print scenario
    print scenario.nodes
    print scenario.links

def log_network_test():
    """"日志网络测试"""
    network = LogNetwork(harder_log, 6)
    node = network.roll()
    i = 0
    while node:
        i = i + 1
        string = str(i) + " "+ str(network)
        #        p_debug(string)
        node = network.roll()

def log_finder_test(log, sce, log_time, config):
    """"
    日志搜索测试:
        暂时还没包含几个测试用例：如多父的情况

    参数
        log：日志文件，如android.txt
        sce：日志场景文件，如logGraph.tu
        log_time：(start_time, end_time)表示搜索的始末时间，格式支持多种，如10-09-05 11:22:30等
        扩展的配置信息：config = {'time_bound': 1, 'search_pattern': SINGLE_PATH_SEARCH}
           time_bound: 代表两个节点间的时间约束 (负数无效，0为不限制，即无穷大，正数就是正数的限制)
           search_pattern: 代表采用哪种搜索模式 （SINGLE_PATH_SEARCH, FULL_GRAPH_SEARCH）

    返回：
        返回一个列表，列表中包含多个json字串格式的结果
    """
    if type(log_time) == type("str"):
        log_time = (log_time, timestamp2string(config["time_window_size"] + string2timestamp(log_time)))
    elif type(log_time) == type(("a", "b")):
        pass
    log_time = (timeParser(log_time[0]), timeParser(log_time[1]))   # timePaser输出格式 "%Y-%m-%d %H:%M:%S"
    if not log_time[0] or not log_time[1]:
        res = LogResults([SceRes(log_time, RES_LOG_TIME_ILLEGAL)])
        msg = res.get_msg()
    log_time = (time.mktime(time.strptime(log_time[0],"%Y-%m-%d %H:%M:%S")),
                time.mktime(time.strptime(log_time[1],"%Y-%m-%d %H:%M:%S")))
    scenario = LogScenario(sce)
    network = LogNetwork(log, config["time_window_size"])
#   fc0a4456->c6587abb->(ad02ac6e,fc66a95e)
    print log_time[0],log_time[1]
    if not network.locate_win(log_time[0]):
        res = LogResults([SceRes(log_time, RES_LOG_TIME_NOT_MATCH)])
        msg = res.get_msg()
    #        print msg
    else:
        finder = LogSceFinder(network, scenario, log_time, config)
        res = finder.find()
        msg = res.get_msg()
    #        print msg
    return msg
#    print network.tot_cnt

def time_check(log, log_time):
    """
    检测日志记录中是否有bug对应的时刻

    参数
        log：日志文件，如android.txt
        log_time：bug时间，格式支持多种，如10-09-05 11:22:30等
    """
    log_time = timeParser(log_time)
    if not log_time: return False
    log_time = time.mktime(time.strptime(log_time,"%Y-%m-%d %H:%M:%S"))
    network = LogNetwork(log)
    if network.locate_win(log_time):
        return True
    else:
        return False

trivial_sce = '../data/trivialSce.tu'
harder_sce = '../data/harderSce.tu'
bl_off = r'../data/bl_off.tu'
bl_on = r'../data/bl_on.tu'
device_display = r'../data/device_dis_play.tu'

trivial_log = r'../data/trivialLog.txt'
harder_log = r'../data/harderLog.txt'
android_log = r'../data/android.txt'
android_0709 = r'../data/android-0709_092745.txt';
android_0805 = r'../data/android-0805_142918.txt';

bl_off_error = r'../data/bl_off_error-android-0510_133530.txt'

if __name__ == "__main__":
    # log_scenario_test(harder_sce)
    # log_network_test()
    # time_check(harder_log, "2019-05-10 12:35:30")
    config = {}
    config["default_time_delay"] = 0
    config["search_pattern"] = FULL_GRAPH_SEARCH
    config["time_window_size"] = 3600*24
    log_time = "2019-08-05 14:30:12"
    print log_finder_test(android_0805, device_display, log_time, config)
    # 05-10 13:35:30 harder
    # 05-10 12:35:53 bl_onfo


# 传引用
#class Foo(object):
#    def __init__(self, arg):
#        self.arg = arg
#
#    def p(self):
#        print self.arg
#
#def bar(foo):
#    foo.arg = 0
#
#foo = Foo(1)
#foo.p()
#bar(foo)
#foo.p()


# 基本类型传值
#def foo(a):
#    a=False
#
#a = True
#foo(a)
#print a


