package com.it.blog.com.it.blog.model.proxy;

//接口中灭有构造方法 不能被实例化
//接口中所有的变量都是静态变量
public class Proxy implements Source{

	private Source source = new OldClass();
	@Override
	public void method() {

	}
}
