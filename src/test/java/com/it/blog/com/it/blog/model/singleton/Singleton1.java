package com.it.blog.com.it.blog.model.singleton;

/**
 * 饿汉式单例（立即加载方式）
 * 饿汉式单利在类加载初始化的时候就创建好一个静态的对象供外部使用，
 * 除非系统重启，这个对象不会改变，所有本身就是线程安全的
 * @author 18576756475
 *
 */
public class Singleton1{
	
	private static Singleton1 singleton = new Singleton1();
	
	/**
	 * 构造方法私有化
	 */
	private Singleton1() {
		
	}
	
	public static Singleton1 getSingleton1 () {
		 
		return singleton;
	}

}
