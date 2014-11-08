package com.baidu.harry.rpc.service;


import com.baidu.harry.rpc.core.EndPoint;
import com.baidu.harry.rpc.expose.HelloService;

@EndPoint("helloService")
public class HelloServiceImpl implements HelloService {
	public String hello(String name) {
		return "Hello " + name;
	}

}
