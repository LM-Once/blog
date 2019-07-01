package com.it.rtc_to_qmd;

import groovy.util.logging.Log;
import groovy.util.logging.Slf4j;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Slf4j
public class PostEsbService extends PostEsbData{


    public static void main(String[] args) {
        String moduleUrl = Constants.getApiProject();
        TreeMap<String,String> parameters = new TreeMap<>();
        /*parameters.put("startDate","2017-01-01 00:00:00");
        parameters.put("endDate", "2019-04-28 00:00:00");
        parameters.put("pageSize","50");
        parameters.put("pageIndex","10");*/

        String responseData = PostEsbData.postEsbData(moduleUrl, parameters);
        System.out.println(responseData);
    }


}
