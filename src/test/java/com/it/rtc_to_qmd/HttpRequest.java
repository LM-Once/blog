package com.it.rtc_to_qmd;

import com.alibaba.fastjson.JSONObject;
import com.sun.xml.internal.ws.util.UtilException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.*;


public class HttpRequest {

    /**
     * 发送 http post 请求，参数以原生字符串进行提交
     * @param url
     * @param stringJson
     * @param headers
     * @param encode
     * @return
     */
    public static String httpPostRaw(String url,String stringJson,Map<String,String> headers, String encode){

        if(encode == null){
            encode = "utf-8";
        }
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpPost httpost = new HttpPost(url);

        //设置header
        httpost.setHeader("Content-type", "application/json");
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpost.setHeader(entry.getKey(),entry.getValue());
            }
        }
        //组织请求参数
        StringEntity stringEntity = new StringEntity(stringJson, encode);
        httpost.setEntity(stringEntity);
        String content = null;
        CloseableHttpResponse  httpResponse = null;
        try {
            //响应信息
            httpResponse = closeableHttpClient.execute(httpost);
            HttpEntity entity = httpResponse.getEntity();
            content = EntityUtils.toString(entity, encode);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            //关闭连接、释放资源
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 发送 http post 请求
     */
    public static String sendPost(String url, TreeMap<String,String> parameters){
        //1.创建httpclint对象
        HttpClient client = new HttpClient();

        // 2.组织请求参数
        List<NameValuePair> paramList = new ArrayList <>();
        if(parameters != null && parameters.size() > 0){
            Set<String> keySet = parameters.keySet();
            for(String key : keySet) {
                paramList.add(new NameValuePair(key, parameters.get(key)));
            }
        }
        NameValuePair[] nvp = paramList.toArray(new NameValuePair[paramList.size()]);

        PostMethod post = new PostMethod(url);
        post.addParameters(nvp);
        post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
        String responseValue = "";
        try {
            client.executeMethod(post);
            responseValue = post.getResponseBodyAsString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(responseValue);
        return responseValue;
    }

    /*public static String doGet(String url, JSONObject params, String charset) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        try {
            if (params != null && !params.isEmpty()) {
                List<org.apache.http.NameValuePair> pairs = new ArrayList<org.apache.http.NameValuePair>(params.size());
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    String value = (String)entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
                url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
            }
            HttpGet httpGet = new HttpGet(url);
            //CloseableHttpResponse response = getClient(url).execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpGet.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, charset);
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        } catch (Exception e) {
            throw new UtilException(e);
        }
    }*/

}
