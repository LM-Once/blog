package com.it.rtc_example;

import com.alibaba.fastjson.JSONObject;
import com.sun.xml.internal.ws.util.UtilException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 80111259 on 2016-10-20.
 */
public class HttpClientUtil {
    public static final String CHARSET = "UTF-8";
    public static final int Connect_Timeout = 60;
    public static final int Connect_Request_Timeout = 60;
    public static final int Socket_Timeout = 1800;
    private static Logger logger = Logger.getLogger(HttpClientUtil.class);

    /**
     * 创建http的客户实例
     *
     * @return
     */
    private static CloseableHttpClient getHttpClient() {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(Connect_Timeout * 1000)
                .setConnectionRequestTimeout(Connect_Request_Timeout * 1000).setSocketTimeout(Socket_Timeout * 1000)
                .build();
        return HttpClientBuilder.create().setDefaultRequestConfig(config).build();
    }

    /**
     * 创建https的客户端实例
     *
     * @return
     */
    private static CloseableHttpClient getHttpsClient() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) {
                    return true;
                }
            }).build();
            RequestConfig config = RequestConfig.custom().setConnectTimeout(Connect_Timeout * 1000)
                    .setConnectionRequestTimeout(Connect_Request_Timeout * 1000).setSocketTimeout(Socket_Timeout * 1000)
                    .build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            return HttpClients.custom().setDefaultRequestConfig(config).setSSLSocketFactory(sslsf).build();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return HttpClients.createDefault();
    }

    /**
     * 创建客户端实例
     *
     * @param url
     * @return
     */
    private static CloseableHttpClient getClient(String url) {
        /*if (url.toUpperCase().startsWith(HttpScheme.HTTPS.name())) {
            return getHttpsClient();
        }*/
        return getHttpClient();
    }

    /**
     * GET请求
     *
     * @param url
     * @param params
     * @return
     */
    public static String doGet(String url, JSONObject params) {
        return doGet(url, params, CHARSET);
    }

    /**
     * POST请求
     *
     * @param url
     * @param params
     * @return
     */
    public static String doPost(String url, JSONObject params) {
        return doPost(url, params, CHARSET);
    }

    /**
     * HTTP Get 获取内容
     *
     * @param url
     *            请求的url地址 ?之前的地址
     * @param params
     *            请求的参数
     * @param charset
     *            编码格式
     * @return 页面内容
     */
    public static String doGet(String url, JSONObject params, String charset) {
        if (StringUtils.isBlank(url)) {
            return null;
        } 
        try {
            if (params != null && !params.isEmpty()) {
                List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    String value = (String)entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
                url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
            }
            HttpGet httpGet = new HttpGet(url);
            logger.debug(url);
            CloseableHttpResponse response = getClient(url).execute(httpGet);
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
    }

    /**
     * HTTP Post 获取内容
     *
     * @param url
     *            请求的url地址 ?之前的地址
     * @param params
     *            请求的参数
     * @param charset
     *            编码格式
     * @return 页面内容
     */
    public static String doPost(String url, JSONObject params, String charset) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        try {
            List<NameValuePair> pairs = null;
            if (params != null && !params.isEmpty()) {
                pairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    String value = (String)entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
            }
            HttpPost httpPost = new HttpPost(url);
            if (pairs != null && pairs.size() > 0) {
                httpPost.setEntity(new UrlEncodedFormEntity(pairs, charset));
            }
            CloseableHttpResponse response = getClient(url).execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
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
    }
}
