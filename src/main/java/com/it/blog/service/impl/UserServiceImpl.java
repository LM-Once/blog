package com.it.blog.service.impl;

import java.util.List;
import java.util.Map;

import com.it.blog.entity.User;

public interface UserServiceImpl {
	
	/**
	 * 查询用户列表
	 * @return
	 */
	List<User> queryUserList();
	
	/**
	 * 保存用户
	 * @param
	 * @return
	 */
	Map<String, Object> saveUser();

	User queryUserInfoByName(User user);

}
