package com.it.blog.com.it.blog.model.singleton;

/**
 * 懒汉式单例模式（延迟加载方式）
 * 该事例虽然用延迟加载实现了懒汉式单例，但在多线程环境下会产生多个single对象，如何改造请看以下方式
 * @author 18576756475
 *
 */
public class Singleton2 {

	
	private static Singleton2 singleton = null;
	
	private Singleton2() {
		
	}
	
	public static Singleton2 getSingleton() {
		if (singleton == null) {
			singleton = new Singleton2();
		}
		return singleton;
	}
}

/**
 * 使用sychronized同步锁
 * @author 18576756475
 *
 */
class Singleton3{
	
	/**
	 * 私有化构造方法
	 */
	private Singleton3 () {
		
	}
	
	private static Singleton3 singleton = null;
	
	
	public static Singleton3 getInstance() {
		synchronized (Singleton3.class) {
			
			//注意：判断一定是要加的，否则会出现线程安全问题
			if (singleton == null) {
				singleton = new Singleton3();
			}
		}
		return singleton;
	}
	
}

/**
 * 在方法上加synchronized同步锁或是用同步代码块对类加同步锁，
 * 此种方式虽然解决了多个实例对象问题，但是该方式运行效率却很低下，
 * 下一个线程想要获取对象，就必须等待上一个线程释放锁以后，才可以继续运行
 */
class Singleton4{
	
	
	private static Singleton4 singleton = null;
	//构造方法
	private Singleton4() {
		
	}
	
	//双重检查
	public static Singleton4 getInstance() {
		
		if (singleton ==null) {
			synchronized (Singleton4.class) {
				singleton = new Singleton4();
			}
		}
		return singleton;
	}
}

/**
 * 静态内部类虽然保证了单例在多线程并发下的线程安全性，
 * 但是在遇到序列化对象时，默认的方式运行得到的结果就
 * 是多例的。这种情况不多做说明了，使用时请注意。
 * @author 18576756475
 *
 */

class Singleton6{
	//私有构造
	private Singleton6 () {
		
	}
	
	private static class InnerObject{
		private static Singleton6 single = new Singleton6();
	}
	
	private static Singleton6 getInstance() {
		return InnerObject.single;
	}
}

/**
 * 静态代码块实现
 * @author 18576756475
 *
 */
class Singleton7{
	private static Singleton7 singleton= null;
	
	static {
		singleton = new Singleton7();
	}
	
	private Singleton7 () {
		
	}
	
	public static Singleton7 getInstance() {
		return singleton;
	}
}

