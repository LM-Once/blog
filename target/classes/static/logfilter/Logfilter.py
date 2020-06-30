# -*- coding: utf-8 -*-
"""
Created on Fri May 17 14:49:27 2019

@author: 80255330
"""
# \\172.17.120.34\log_dump\logdata06\19031&19331\19031&19331\Bug\2150557
# -*- encoding: utf8 -*-

# -*- coding: utf-8 -*-
# pip install shutil

import os
import shutil
import sys
from loglexer import BtLogLexer
from DFStreeSearch.token import Token
import datetime
from DFStreeSearch import logScenario, logNetwork, timeParser
import json
import time


class Logfilter:

    def filer_hci_auto(self, bug_id, bug_time, bug_log_path, btsnoop_path):
        fileList = os.listdir(bug_log_path)
        max = 0
        srcFilenamehci = None
        desFilenamehci = None
        error_code = '1'
        for filename in fileList:
            if 'debug' in filename:
                fileList2 = os.listdir(bug_log_path + '/' + filename)
                for filename2 in fileList2:
                    if filename2 == 'btsnoop_hci':
                        srcFilenamehci = bug_log_path + '/' + filename + '/' + filename2
                        desFilenamehci = 'F:/packages/log_package/' + time.strftime('%Y-%m-%d-%H-%M-%S',
                                                                                    time.localtime()) + '/' + filename2
                        shutil.copytree(srcFilenamehci, desFilenamehci)
                        hci = self.filter_log(bug_id, bug_time, desFilenamehci, btsnoop_path)
                        if hci:
                            error_code = '0'  # hci合法
                        else:
                            error_code = '2'  # hci文件时间戳错误
                            return error_code
                    else:
                        error_code = '1'  # hci文件缺失
        return error_code

    def filter_hci_manual(self, bug_id, bug_time, bug_log_zip, btsnoop_path):
        fileList1 = os.listdir(bug_log_zip)
        hci = None
        error_code = '1'
        for filename1 in fileList1:
            if filename1 == 'pre_log':
                fileList2 = os.listdir(bug_log_zip + '/' + filename1)
                for filename2 in fileList2:
                    if 'debug' in filename2:
                        fileList3 = os.listdir(bug_log_zip + '/' + filename1 + '/' + filename2)
                        for filename3 in fileList3:
                            if filename3 == 'btsnoop_hci':
                                filehcipath = bug_log_zip + '/' + filename1 + '/' + filename2 + '/' + filename3
                                hci = self.filter_log(bug_id, bug_time, filehcipath, btsnoop_path)
                                if hci:
                                    error_code = '0'  # hci合法
                                else:
                                    error_code = '2'  # hci文件时间戳错误
                                return error_code
                            else:
                                error_code = '1'  # hci文件缺失
        return error_code

    def filter_log(self, bug_id, bug_time, bug_log, btsnoop_path):
        bug_time = timeParser.timeParser(bug_time)
        if 'hci' in bug_log:
            return self.check_hci_time(bug_log, bug_time, btsnoop_path)
        else:
            return self.check_log_time(bug_log, bug_time)

    def check_hci_time(self, hcipath, bug_time, btsnoop_path):
        g = os.walk(hcipath)
        for path, dir_list, file_list in g:
            for file_name in file_list:
                with os.popen('python ' + btsnoop_path + ' ' + os.path.join(path, file_name)) as f:
                    print f.readline
                    duration = f.readline().replace('\n', '').replace('2019-', '')
                    bug_time = bug_time.replace('2019-', '')
                    if '.' not in bug_time:
                        bug_time = bug_time + '.000000'
                    du_array = duration.split(' % ')
                    print bug_time, du_array
                    if not self.judge_time_in_block(bug_time, du_array, '%m-%d %H:%M:%S.%f'):
                        return False
            return True

    def check_log_time(self, logpath, bug_time):
        g = os.walk(logpath)
        print logpath
        for path, dir_list, file_list in g:
            for file_name in file_list:
                print os.path.join(path, file_name)
                if not logNetwork.time_check(os.path.join(path, file_name), bug_time):
                    return False
        return True

    def get_log_time_block(self, find_time, str_file_path):
        import codecs
        f = codecs.open(str_file_path, 'r', None)
        l = BtLogLexer(f)
        token = l.read()
        while token != Token.END:
            token = l.read()
        block_time = l.get_lexe_time_block()
        return self.judge_time_in_block(find_time, block_time, '%m-%d %H:%M:%S.%f')

    def judge_time_in_block(self, find_time, block_time, format):
        first_d = datetime.datetime.strptime(block_time[0], format)
        # print first_d
        last_d = datetime.datetime.strptime(block_time[1], format)
        # print last_d
        find_d = datetime.datetime.strptime(find_time, format)
        # print find_d
        if (find_d - first_d).total_seconds() > 0 and (last_d - find_d).total_seconds() > 0:
            return True
        return False

    def get_log_result_demo(self, path, tu, bug_time, bug_id, config_str, btsnoop_path):
        final_result = {
            "file_list": []
        }
        config = json.loads(config_str)
        bug_times = timeParser.timeParser(bug_time)
        hci = self.filter_log(bug_id, bug_times, path, btsnoop_path)
        final_result["hci"] = hci
        loglist = os.listdir(path)
        for file_name in loglist:
            if 'android' in file_name:
                json_result_list = logNetwork.log_finder_test(path + '/' + file_name, tu, bug_time, config)
                final_result[file_name] = json_result_list
                final_result["file_list"].append(file_name)
        json_result = json.dumps(final_result)
        return json_result

    def get_log_result_manual(self, path, tu, bug_time, bug_id, config_str):
        config = json.loads(config_str)
        fileList1 = os.listdir(path)
        for filename1 in fileList1:
            if filename1 == 'pre_log':
                fileList2 = os.listdir(path + '/' + filename1)
                for filename2 in fileList2:
                    if 'debug' in filename2:
                        fileList3 = os.listdir(path + '/' + filename1 + '/' + filename2)
                        for filename3 in fileList3:
                            if '-' in filename3:
                                filelogpath = path + '/' + filename1 + '/' + filename2 + '/' + filename3 + '/apps'
                                loglist = os.listdir(filelogpath)
                                for logfile in loglist:
                                    if 'android' in logfile:
                                        print 'logfile ', config
                                        json_result_list = logNetwork.log_finder_test(filelogpath + '/' + logfile, tu,
                                                                                      bug_time, config)
                                        print json_result_list
                                        if json_result_list is not None:
                                            final_result = {}
                                            for i in range(len(json_result_list)):
                                                json_result = json.loads(json_result_list[i])
                                                if json_result['Result'] == 'hit error node' or json_result[
                                                    'Result'] == 'incomlete error' or json_result[
                                                    'Result'] == 'Succeeded':
                                                    return json_result_list
        return []
        #                                             final_result['msg'] = 'found'
        #                                             final_result['tuResult'] = json_result_list
        #                                             return json.dumps(final_result)
        #                             else:
        #                                 final_result = {}
        #                                 final_resultp['msg'] = 'noFile'
        #                                 return json.dumps(final_result)
        #
        # final_result = {}
        # final_resultp['msg'] = 'timeError'
        # return json.dumps(final_result)

    def get_log_result_auto(self, bug_log_path, tu, bug_time, bug_id, config_str):
        config = json.loads(config_str)
        fileList = os.listdir(bug_log_path)
        max = 0
        srcFilenamehci = None
        desFilenamehci = None
        srcFilename = None
        desFilename = None
        target_file = None
        date_no = 0
        for filename in fileList:
            if 'debug' in filename:
                fileList2 = os.listdir(bug_log_path + '/' + filename)
                for filename2 in fileList2:
                    if filename2 == 'btsnoop_hci':
                        srcFilenamehci = bug_log_path + '/' + filename + '/' + filename2
                        desFilenamehci = 'F:/packages/log_package/' + time.strftime('%Y-%m-%d-%H-%M-%S',
                                                                                    time.localtime()) + '/' + filename2
                        shutil.copytree(srcFilenamehci, desFilenamehci)
                    if '-' in filename2:
                        date_no_str = filename2.replace('-', '')
                        date_no = int(date_no_str)
                        if date_no > max:
                            date_no = max
                            target_file = filename2
                srcFilename = bug_log_path + '/' + filename + '/' + target_file
                desFilename = 'F:/packages/log_package/' + time.strftime('%Y-%m-%d-%H-%M-%S',
                                                                         time.localtime()) + '/' + target_file
                shutil.copytree(srcFilename, desFilename)
        loglist = os.listdir(desFilename + '/apps')
        for logfile in loglist:
            if 'android' in logfile:
                json_result_list = logNetwork.log_finder_test(desFilename + '/apps/' + logfile, tu, bug_time, config)
                if json_result_list is not None:
                    for i in range(len(json_result_list)):
                        json_result = json.loads(json_result_list[i])
                        if json_result['Result'] == 'hit error node' or json_result['Result'] == 'incomlete error' or \
                                json_result['Result'] == 'Succeeded':
                            return json_result_list
        return []
        #                     final_result['msg'] = 'found'
        #                     final_result['tuResult'] = json_result_list
        #                     return json.dumps(final_result)
        #                 else:
        #                     final_result = {}
        #                     final_resultp['msg'] = 'noFile'
        #                     return json.dumps(final_result)
        #
        # final_result = {}
        # final_resultp['msg'] = 'timeError'
        # return json.dumps(final_result)


if __name__ == '__main__':
    # print Logfilter().test_copy()
    # print LogFilterUitl().filter_log('', '05-09 23:51:53.435', '')
    # print 'result: ' + Logfilter().filter_hci_manual('', '2019-05-01 13:35:30', 'D:\log\lograr', 'D:\\btsnoop.py')
    config = {}
    config['default_time_delay'] = 1
    config['search_pattern'] = True
    config['time_window_size'] = 600
    config_str = json.dumps(config)
# print Logfilter().get_log_result_manual('D:\My Documents\Desktop\BTlog\log\lograr2',
#                                         'D:\My Documents\Desktop\BTlog\logfilter\data\\blue_off.tu',
#                                         '2019-05-10 12:35:54', '', config_str)
    print Logfilter().get_log_result_demo('F:\packages\log_package_file\error',
                                      'F:\packages\\tu_file\AD027_AD030_AD031_AD061_AD065\SDP\\test1.tu',
                                      '2019-05-10 13:35:30', '', config_str, 'C:\Users\Administrator\Desktop\web\python\logfilter\\btsnoop.py')
