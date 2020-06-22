package com.it.utils.lambda;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import java.util.TreeMap;


public class TestMain {

    /*public static void main(String[] args) {
        TreeMap<String, Object> paramsMap= Maps.newTreeMap();
        paramsMap.put("1","BT蓝牙模块");
        paramsMap.put("2","WIFI模块");
        String result = new TestMain().getResuleLam(paramsMap);
        System.out.println(result);
    }*/
    public static void main(String[] args) {

    }

    public String getBtResult(final TreeMap paramsMap){
        SpecialTest specialTest = new SpecialTest() {
            @Override
            public Object getBtModel() {
                return JSON.toJSONString(paramsMap);
            }
        };
        Object btModel = specialTest.getBtModel();
        return btModel==null?null:btModel.toString();
    }

    public String getResuleLam(final TreeMap paramsMap){
        SpecialTest specialTest = () -> JSON.toJSONString(paramsMap);
        Object btModel = specialTest.getBtModel();
        return btModel==null?null:btModel.toString();
    }
}
