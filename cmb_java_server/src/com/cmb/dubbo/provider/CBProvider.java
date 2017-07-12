package com.cmb.dubbo.provider;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cmb.user.pojo.User;
import com.cmb.user.service.IUserService;

public class CBProvider {

	private static ApplicationContext ac = null; 
	
	static{
		ac = new ClassPathXmlApplicationContext("classpath:com/cmb/dubbo/provider/springmvc.xml");   
		
	}

	public static void main(String[] args) throws Exception{
		IUserService userService = (IUserService) ac.getBean("userService");  
		User response = userService.findUser();
		System.out.println(response.getName());
		while (true) {
			Thread.sleep(1000*10);
		}
		
	}
	
}
