package com.it.service;

import com.it.Common.RetResult;
import com.it.service.hystric.SchedualServiceHystric;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 18576756475
 * @version V1.0
 * @ClassName UserService
 * @Description 远程服务调用接口
 * @Date 2020-01-15 11:10:00
 * 说明：在SpringCloud Netflix栈中，各个微服务都是以http接口的形式暴露自身服务的，
 * 我们可以使用JDK原生的URLConnect，Apache的HttpClient,Netty 的异步 HTTP Client，
 * spring的RestTemplate 但feign用起来更方便简洁
 **/
@FeignClient(value = "SERVICE-PROVIDE", fallbackFactory = SchedualServiceHystric.class)
public interface UserService {

    @RequestMapping(value =  "/api/login/doLogin",method = RequestMethod.POST)
    RetResult doLogin(@RequestParam("username") String username,
                      @RequestParam("password") String password);
}
