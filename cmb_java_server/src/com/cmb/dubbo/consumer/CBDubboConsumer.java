package com.cmb.dubbo.consumer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cmb.user.pojo.User;
import com.cmb.user.service.IUserService;

public class CBDubboConsumer {

private static ApplicationContext ac = null; 
	
	static{
		ac = new ClassPathXmlApplicationContext("classpath:com/cmb/dubbo/consumer/springmvc.xml");   
		
	}

	public static void main(String[] args) throws Exception{
		for (int i = 0; i < 100; i++) {
			IUserService userService = (IUserService) ac.getBean("userRemoteService");  
			User response = userService.findUser();
			System.out.println(response.getName());

		}

		/*while (true) {
			Thread.sleep(1000*10);
		}*/
		
	}
}
