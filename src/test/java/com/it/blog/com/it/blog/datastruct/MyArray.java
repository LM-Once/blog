package com.it.blog.com.it.blog.datastruct;

public class MyArray {
	
	private int [] intArray;
	
	private int elems; //实际有效长度
	
	private int length;
	
	public MyArray() {
		elems = 0;
		length = 50;
		intArray = new int[length];
	}
	
	public MyArray(int lenth) {
		elems = 0;
		length = lenth;
		intArray = new int[length];
	}
	
	//获取数组的有效长度
	public int getSize() {
		return elems;
	}
	public static void main(String[] args) {
		
		
		
	}

}
