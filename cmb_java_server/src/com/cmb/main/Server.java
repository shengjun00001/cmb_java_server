package com.cmb.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Server {

	public static void main(String[] args) throws InterruptedException {
		System.out.println("server start...");
		//ApplicationContext context = new ClassPathXmlApplicationContext("springmvc.xml");
		
		while (true) {
			Thread.sleep(60*1000);
			// 主线程暂时啥也不做
		}
	}
	
}
