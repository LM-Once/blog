package com.it.blog.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import com.it.blog.entity.User;

@Mapper
public interface UserMapper {

	/**
	 * 查询用户列表
	 * @return
	 */
	List<User> queryUserList();
	
	/**
	 * 保存用户
	 * @param user
	 * @return
	 */
	int saveUser(User user);

	User queryUserInfoByName(User user);

}
