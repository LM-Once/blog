package com.it.blog.com.it.blog.model.abstactfactory;

public class FactoryA implements produce{

	@Override
	public food get() {
		
		return new A();
	}
	
	

}
