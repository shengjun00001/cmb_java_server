package com.cmb.user.service.impl;

import org.springframework.stereotype.Service;

import com.cmb.user.pojo.User;
import com.cmb.user.service.IUserService;
import com.cmb.utils.CBLogUtils;

@Service("userService")
public class UserServiceImpl implements IUserService{

	@Override
	public User findUser() {
		User user = new User();
		CBLogUtils.Log("server 4");
		user.setName("yangyuy");
		return user;
	}

}
