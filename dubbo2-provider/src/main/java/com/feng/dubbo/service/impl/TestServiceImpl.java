package com.feng.dubbo.service.impl;

import java.io.Serializable;

import com.feng.dubbo.service.TestService;



public class TestServiceImpl implements TestService,Serializable {

	public String sayHello() {
		System.out.println("Hello Wolrd");
		return "Hello World";
	}

}
