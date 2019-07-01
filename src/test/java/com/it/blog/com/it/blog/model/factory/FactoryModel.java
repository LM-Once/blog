package com.it.blog.com.it.blog.model.factory;

/**
 * 常用的工厂模式是静态工厂，
 * 类似于常用的工具类Utils等辅助效果
 * @author 18576756475
 *
 */
public class FactoryModel {
	
	private FactoryModel(){
		
	}
	
	/**
	 * 获取FactoryA实例
	 * @return
	 */
	public static Food getFactoryA() {
		
		return new FactoryA();
	}
	
	/**
	 * 获取FactoryB实例
	 * @return
	 */
	public static Food getFactoryB() {
		return new FactoryB();
	}
	
	/**
	 * 获取FactoryC实例
	 * @return
	 */
	public static Food getFactoryC() {
		return new FactoryC();
	}
	
}

class Client{
	
	//客户端只需要将相应的参数传入即可得到对象
	//用户不需要了解工厂内部的逻辑
	public void getInstance(String name) {
		
		Food food =null;
		if (name.equals("FactoryA")) {
			food = FactoryModel.getFactoryA();
		}else if (name.equals("FactoryB")) {
			food = FactoryModel.getFactoryB();
		}
	}
}
