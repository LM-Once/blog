# -*- coding: utf-8 -*-
"""
Log searcher API for client

Created on Fri Jul 19 11:47:03 2019

@author: 莫楚轩 S9023069

"""
#%%
def todoTest():
    TODO

todoTest()
#%%
class LogScenario:
    """日志场景图, 从日志文件创建，如logScenario.tu
    """
        
    def __init__(self, filename):
    
    def get_root(self):
        """返回日志场景图的根节点"""

class LogNetwork(object):
    """日志网络图，从日志记录文件创建，如androi.txt, 提供一定时间跨度totDuration的序列节点流输出"""
    
    def __init__(self, filename, tot_duration=WINDOW_DURATION):
        """构造函数
        
        参数：
            filename: 待加载的日志记录文件
            tot_duration：时间窗口的时间跨度
        """
        
    
    def roll(self):
        """每次调用输出日志记录中的下一个节点，同时维护内存中特定时间跨度的节点缓冲区，直到EOF
        
        返回：
            GeneralNode: 窗口中的首节点
            None: 到达文件尾；
        """
    
    def locate_win(self, log_time):
        """将窗口的尾部定位到给定的时刻
        
        返回：
            True定位成功，False定位失败
         
        采用二分搜索进行优化
        
        window的特殊情况：time hole, t2-t1 < duration, t4-t3<duration, t3-t2>duration
        另外locate_win，当遇到EOF即可停止，不需要项roll一样继续读，因为roll从窗口头开始读，这里是尾
        """


class LogSceFinder(object):
    """日志搜索器，由日志网络、日志场景图、目标日志时刻创建"""
    
    def __init__(self, logNetwork, logScenario, log_time):
    
    def find(self):
        """搜索场景图在日志记录中出现的情况"""  
   
class SceRes(object):
    """一个场景图自根节点符合后的搜索结果"""
    
    def __init__(self, log_time, res_type, nodes=None, err_nodes=None):
        """构造函数
        
        参数：
            log_time：待搜索场景对应的bug的时刻
            res_type：结果类型，包括bug的时刻错误（log_time格式非法RES_LEGAL_PATH
                或者不对应日志中任何时间点RES_LOG_TIME_NOT_MATCH）
                至少找到一条合法路径RES_LEGAL_PATH，找到错误节点RES_HIT_ERROR_NODE，
                根节点符合但其他均不符合RES_INCOMPLETE
            nodes：符合场景图中的节点信息
            err_nodes：错误节点信息
        """
        
    def to_json(self):
        """返回该结果的json格式，如下
{
    "Links":[
        {
            "begin":"fc0a4456",
            "end":"ad02ac6e",
            "ID":0
        },
        {
            "begin":"fc0a4456",
            "end":"fc66a95e",
            "ID":1
        }
    ],
    "Error nodes":[
        {
            "content":"enable fail",
            "children_number":0,
            "attr":1,
            "timestamp":1557466533,
            "parents_number":1,
            "priority":-1,
            "tag":"BluetoothAdapter",
            "ID":"fc66a95e"
        }
    ],
    "Result":"hit error node",
    "Logtime":"2019-05-10 13:35:30",
    "Nodes":[
        {
            "content":"in enable",
            "children_number":2,
            "attr":1,
            "timestamp":1557466530,
            "parents_number":0,
            "priority":-1,
            "tag":"BluetoothAdapter",
            "ID":"fc0a4456"
        },
        {
            "content":"enable sucess",
            "children_number":0,
            "attr":1,
            "timestamp":1557466532,
            "parents_number":1,
            "priority":-1,
            "tag":"BluetoothAdapter",
            "ID":"ad02ac6e"
        },
        {
            "content":"enable fail",
            "children_number":0,
            "attr":1,
            "timestamp":1557466533,
            "parents_number":1,
            "priority":-1,
            "tag":"BluetoothAdapter",
            "ID":"fc66a95e"
        }
    ],
    "class":"Found a error node"
}        
        """
     
class LogResults(object):
    """日志搜索结果，由LogSceFinder.find()返回，包括多个SceRes结果"""
    
    def __init__(self, res = None):
    
    def print_legal_res(self): 
        """打印出符合日志场景的合法日志记录"""
        
    def get_msg(self):
        """返回json子串格式的三种搜索结果列表"""

def log_network_test():
    """"日志网络测试"""
    network = LogNetwork(harder_log, 6)
    node = network.roll()
    i = 0
    while node:
        i = i + 1
        string = str(i) + " "+ str(network)
        p_debug(string)
        node = network.roll()

def log_finder_test(log, sce, log_time, duration):
    """"
    日志搜索测试
    
    参数
        log：日志文件，如android.txt
        sce：日志场景文件，如logGraph.tu
        log_time：bug时间，格式支持多种，如10-09-05 11:22:30等
        duration：指定加载对应时间窗口的日志记录，其持续时间为duration
    """
    log_time = timeParser(log_time) # 传进来的是 "%Y-%m-%d %H:%M:%S" 该格式
    if not log_time:
        res = LogResults([SceRes(log_time, RES_LOG_TIME_ILLEGAL)])
        msg = res.get_msg()
        print msg
    log_time = time.mktime(time.strptime(log_time,"%Y-%m-%d %H:%M:%S"))
    scenario = LogScenario(sce)
    network = LogNetwork(log, duration)
    if not network.locate_win(log_time):
        res = LogResults([SceRes(log_time, RES_LOG_TIME_NOT_MATCH)])
        msg = res.get_msg()
        print msg
    else:        
        finder = LogSceFinder(network, scenario, log_time)
        res = finder.find()
        msg = res.get_msg()
        print msg
    print network.tot_cnt

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
trivial_log = r'../data/trivialLog.txt'
harder_sce = '../data/harderSce.tu'
harder_log = r'../data/harderLog.txt'
android_log = r'../data/android.txt'
android_0709 = r'../data/android-0709_092745.txt';

if __name__ == "__main__":
#    log_network_test()
#    time_check(harder_log, "2019-05-10 12:35:30")
    
    log_finder_test(harder_log, harder_sce, "2019-05-10 13:35:30", 300)
