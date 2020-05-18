package com.it.controller;

import com.google.common.collect.Sets;
import com.it.Common.RetResponse;
import com.it.Common.RetResult;
import com.it.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

/**
 * @author 18576756475
 * @version V1.0
 * @ClassName UserController
 * @Description 用户控制层
 * @Date 2020-01-15 11:14:15
 * https://blog.csdn.net/u010502101/article/details/81989756
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/doLogin",method = RequestMethod.POST)
    public RetResult doLogin( String username, String password){
        return  userService.doLogin(username,password);
    }

    /**
     * 服务熔断
     * @param username
     * @param password
     * @return
     * 说明：当某个服务出现异常时，熔断该服务，
     * 快速返回指定的错误信息，当服务正常时，恢复熔断
     */
    @RequestMapping(value = "/testServiceHystrix",method = RequestMethod.POST)
    @HystrixCommand(fallbackMethod = "testHystrix")
    public RetResult testServiceHystrix( String username, String password){
        RetResult result = new RetResult();
        if (result == null){
            throw new RuntimeException("测试服务熔断机制");
        }
        return  userService.doLogin(username,password);
    }

    public RetResult testHystrix(String username, String password){
        Set user = Sets.newHashSet();
        user.add(username);
        user.add(password);
        user.add("account or password is error ");
        return RetResponse.makeOkData(user);
    }
}
