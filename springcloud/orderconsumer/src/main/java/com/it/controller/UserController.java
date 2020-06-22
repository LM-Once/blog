package com.it.controller;

import com.it.Common.RetResult;
import com.it.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 18576756475
 * @version V1.0
 * @ClassName UserController
 * @Description 用户控制层
 * @Date 2020-01-15 11:14:15
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    public RetResult doLogin( String username, String password){
        return  userService.doLogin(username,password);
    }
}
