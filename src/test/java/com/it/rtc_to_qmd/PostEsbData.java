package com.it.rtc_to_qmd;

import java.util.Map;
import java.util.TreeMap;
import com.it.rtc_to_qmd.Constants;
/**
 * ESB通用请求
 */
public class PostEsbData {

    /**
     * ESB通用请求方法
     * @param moduleUrl 模块路径
     * @param parameters
     * @return
     */
    public static  String postEsbData(String moduleUrl, TreeMap<String,String> parameters){

        long timestamp = System.currentTimeMillis();

        parameters.put("app_id",Constants.getConfigErpApiAppid());
        parameters.put("timestamp",timestamp + "");
        parameters.put("sign",sign(moduleUrl, parameters ,Constants.getConfigSecret()));
        parameters.put("moduleUrl",moduleUrl);

        return HttpRequest.sendPost(Constants.getConfigApiUrl() + moduleUrl , parameters);
    }

    /**
     * 签名
     * @param url
     * @param params
     * @param secret
     * @return
     */
    public static String sign(String url, TreeMap<String, String> params,
                              String secret) {
        StringBuilder builder = new StringBuilder(url);
        builder.append("\n");
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> pair : params.entrySet()) {
                builder.append(pair.getKey());
                builder.append("=");
                builder.append(pair.getValue());
                builder.append("\n");
            }
        }
        builder.append(secret);
        return Md5Utils.md5(builder.toString());
    }
}
