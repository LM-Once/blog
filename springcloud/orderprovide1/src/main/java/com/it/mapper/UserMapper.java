package com.it.mapper;

import com.it.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> findUserInfo(String username);

    User queryUserInfo(@Param("username") String username,
                             @Param("password") String password);

}
