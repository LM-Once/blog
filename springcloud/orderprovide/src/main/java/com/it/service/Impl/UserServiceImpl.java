package com.it.service.Impl;

import com.it.domain.User;
import org.springframework.stereotype.Service;
import java.util.List;


public interface UserServiceImpl {

    List<User> findUserInfo(String username);

    User queryUserInfo(String username, String password);
}
