package com.baidu.harry.rpc.service;


import com.baidu.harry.rpc.core.EndPoint;

@EndPoint("helloService")
public class HelloServiceImpl implements HelloService {
	public String hello(String name) {
		return "Hello " + name;
	}

}
