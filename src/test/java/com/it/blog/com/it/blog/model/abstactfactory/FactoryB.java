package com.it.blog.com.it.blog.model.abstactfactory;

public class FactoryB implements produce{

	@Override
	public food get() {
		
		return new B();
	}

}
