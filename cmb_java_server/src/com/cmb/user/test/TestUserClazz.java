package com.cmb.user.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cmb.user.pojo.User;
import com.cmb.user.service.IUserService;

public class TestUserClazz {

	private static ApplicationContext ac = null; 
	
	static{
		ac = new ClassPathXmlApplicationContext("springmvc.xml");   
		
	}

	@Test
	public void testFindUser() {
		IUserService userService = (IUserService) ac.getBean("userService");  
		User response = userService.findUser();
		System.out.println(response.getName());
	}
	
}
