package com.it.blog.com.it.blog.datastruct;

import java.util.Stack;

import org.apache.commons.collections.ArrayStack;

public class MyStack {
	
	public static void main(String[] args) {
		new MyStack().sort();
		/*MyStack stack = new MyStack(3);
        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.println(stack.peek());
        while(!stack.isEmpty()){
            System.out.println(stack.pop());
        }*/
	}
	
	private int[] array;
    private int maxSize;
    private int top;
    
    public MyStack() {}
    
    public MyStack(int size){
        this.maxSize = size;
        array = new int[size];
        top = -1;
    }
    public String sort() {
    	String  str = "HOW YOU ARE";
    	
    	ArrayStack stack = new ArrayStack();
    	char[] charArray = str.toCharArray();
    	for (int i = 0; i < charArray.length; i++) {
    		stack.push(charArray[i]);
		}
    	while(!stack.isEmpty()) {
    		System.out.println(stack.pop());
    	}
    	
    	return charArray.toString();
    }
    //压入数据
    public void push(int value){
        if(top < maxSize-1){
            array[++top] = value;
        }
    }
     
    //弹出栈顶数据
    public int pop(){
        return array[top--];
    }
     
    //访问栈顶数据
    public int peek(){
        return array[top];
    }
     
    //判断栈是否为空
    public boolean isEmpty(){
        return (top == -1);
    }
     
    //判断栈是否满了
    public boolean isFull(){
        return (top == maxSize-1);
    }
    
    
}	
