package com.it.rtc_example;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by 80111259 on 2017-02-09.
 */
public class RtcServerImpl {
    private static final String appid = "rtc";
    private static final String secret = "rtcapi";
    private static final String rtcUrl = "https://rtctest2.myoppo.com:9444/ccm/web";
    private static final String URL = "http://rtchelpertest.myoppo.com";

    private static String sign(String moduleUrl, TreeMap<String, Object> params, String secret){
        StringBuilder builder = new StringBuilder(moduleUrl);
        builder.append("\n");

        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, Object> pair : params.entrySet()) {
                if (pair.getValue() == null) {
                    continue;
                }
                builder.append(pair.getKey());
                builder.append("=");
                builder.append(pair.getValue());
                builder.append("\n");
            }
        }
        builder.append(secret);

        String sign = Md5Utils.md5(builder.toString());
        return sign;
    }

    private static JSONObject callRtcApi(String apiName, JSONObject caseBug, RequestMethod requestMethod) throws IOException {
        //String moduleUrl = "/request/create";
        long timestamp = System.currentTimeMillis();
        caseBug.put("app_id",appid);
        caseBug.put("moduleUrl",apiName);
        caseBug.put("timestamp",timestamp + "");
        TreeMap<String,Object> treeMap = new TreeMap<>();
        for(Map.Entry<String,Object> entity : caseBug.entrySet()){
            treeMap.put(entity.getKey(),entity.getValue());
        }
        caseBug.put("sign",sign(apiName,treeMap,secret));
        System.out.println("接口输入的请求参数：" + treeMap);
        String response = "";
        switch(requestMethod){
            case POST:
                response = HttpClientUtil.doPost(URL + apiName, caseBug);
                break;
            case GET:
                response = HttpClientUtil.doGet(URL + apiName, caseBug);
                break;
        } 
        JSONObject json = JSONObject.parseObject(response);
        //RtcResultDto rrd = JSONObject.toJavaObject(json,RtcResultDto.class);
        return json;
    }

    //IREQ_001	创建用例缺陷
    public static JSONObject createRtcBug(JSONObject data) throws IOException {
        if(data == null){
            return null;
        }
        return RtcServerImpl.callRtcApi("/rtcapi/qmdapi/request/create", data, RequestMethod.POST);
    }

    //IREQ_002	获取项目区域
    public static JSONObject getRtcProject(JSONObject data) throws IOException {
        if(data == null){
            return null;
        }
        return RtcServerImpl.callRtcApi("/rtcapi/qmdapi/project", data, RequestMethod.GET);
    }

    //IREQ_003	获取团队
    public static JSONObject getRtcTeam(JSONObject data) throws IOException {
        if(data == null){
            return null;
        }
        return RtcServerImpl.callRtcApi("/rtcapi/qmdapi/team", data, RequestMethod.GET);
    }

    //IREQ_004	查询缺陷
    public static JSONObject getRtcBug(JSONObject data) throws IOException {
        if(data == null){
            return null;
        }
        return RtcServerImpl.callRtcApi("/rtcapi/qmdapi/request", data, RequestMethod.GET);
    }

    //IREQ_005	获取时间线iteration
    public static JSONObject getRtcIteration(JSONObject data) throws IOException {
        if(data == null){
            return null;
        }
        return RtcServerImpl.callRtcApi("/rtcapi/qmdapi/iteration", data, RequestMethod.GET);
    }

    //IREQ_006	获取类型数据(如归档依据)
    public static JSONObject getRtcCategory(JSONObject data) throws IOException {
        if(data == null){
            return null;
        }
        return RtcServerImpl.callRtcApi("/rtcapi/qmdapi/category", data, RequestMethod.GET);
    }

    //IREQ_007	获取工作项属性
    public static JSONObject getRtcAttribute(JSONObject data) throws IOException {
        if(data == null){
            return null;
        }
        return RtcServerImpl.callRtcApi("/rtcapi/qmdapi/attribute", data, RequestMethod.GET);
    }

    //IREQ_008	获取项目区域属性枚举
    public static JSONObject getRtcEnumeration(JSONObject data) throws IOException {
        if(data == null){
            return null;
        }
        return RtcServerImpl.callRtcApi("/rtcapi/qmdapi/enumeration", data, RequestMethod.GET);
    }

    //IREQ_009	获取工作项类型
    public static JSONObject getRtcRequestType(JSONObject data) throws IOException {
        if(data == null){
            return null;
        }
        return RtcServerImpl.callRtcApi("/rtcapi/qmdapi/request/type", data, RequestMethod.GET);
    }

    //IREQ_010	获取工作项状态
    public static JSONObject getRtcRequestState(JSONObject data) throws IOException {
        if(data == null){
            return null;
        }
        return RtcServerImpl.callRtcApi("/rtcapi/qmdapi/request/state", data, RequestMethod.GET);
    }

    //IREQ_011	获取工作项历史记录
    public static JSONObject getRtcRequestHistory(JSONObject data) throws IOException {
        if(data == null){
            return null;
        }
        return RtcServerImpl.callRtcApi("/rtcapi/qmdapi/request/history", data, RequestMethod.GET);
    }

    //IREQ_012	获取工作项评论
    public static JSONObject getRtcRequestComment(JSONObject data) throws IOException {
        if(data == null){
            return null;
        }
        return RtcServerImpl.callRtcApi("/rtcapi/qmdapi/request/comment", data, RequestMethod.GET);
    }

    public static void main(String[] args) {
        Map<String, Object> map =new HashMap<>();
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(map));
        try {
            JSONObject responseData = RtcServerImpl.getRtcProject(jsonObject);
            System.out.println(responseData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
