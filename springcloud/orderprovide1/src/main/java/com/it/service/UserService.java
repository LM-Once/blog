package com.it.service;

import com.it.domain.User;
import com.it.mapper.UserMapper;
import com.it.service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;



@Service
public class UserService implements UserServiceImpl {

    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> findUserInfo(String username) {
        return userMapper.findUserInfo(username);
    }

    @Override
    public User queryUserInfo(String username, String password) {
        return userMapper.queryUserInfo(username, password);
    }

}
