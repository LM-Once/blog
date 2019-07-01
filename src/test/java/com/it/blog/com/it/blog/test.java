package com.it.blog.com.it.blog;


import java.io.*;
import java.util.Map;
import java.util.TreeMap;

class test{

	public static void main(String args[]){

		Map<String,Object> map = new TreeMap<>();
		map.put("name","xixi");
		map.put("value",45);
		System.out.println(map.entrySet());
		System.out.println(map.keySet());
	}
}



