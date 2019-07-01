package com.it.blog.com.it.blog.oppo_tt;



import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequest {


	public static String sendPost(String url,List<NameValuePair> params){
		//1.创建httpclint对象
		HttpClient client = new HttpClient();
		
		//NameValuePair nvp = new NameValuePair("username","xixi");
		
		NameValuePair[] nvp = params.toArray(new NameValuePair[params.size()]);
		
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

	public static  void main(String[] args){

		List<NameValuePair> list = new ArrayList<NameValuePair>();
		
		
		list.add(new NameValuePair("username","test"));
		list.add(new NameValuePair("secret","123456"));
		System.out.println(list);
		HttpRequest.sendPost("http://127.0.0.1:8081/blog/queryUserList",list);
	}
}

