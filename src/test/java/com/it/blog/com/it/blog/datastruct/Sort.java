package com.it.blog.com.it.blog.datastruct;

public class Sort {
	
	
	public static void main(String[] args) {
		
	}
	
	/**
	 * 冒泡排序
	 */
	public void sort() {
		int [] array = {4,2,8,9,5,7,6,1,3};
		
		for (int i = 1; i < array.length; i++) {
			
			for (int j = 0; j < array.length-i; j++) {
				if (array[j]>array[j+1]) {
					int tem = array[j];
					array[j] = array[j+1];
					array[j+1] =tem;
				}
			}
		}
		System.out.println(array);
		for (int i = 0; i < array.length; i++) {
			System.out.println(array[i]);
			
		}
	}
	
}
