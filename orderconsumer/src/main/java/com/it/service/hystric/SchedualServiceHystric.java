package com.it.service.hystric;

import com.it.Common.RetResponse;
import com.it.Common.RetResult;
import com.it.service.UserService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author 18576756475
 * @version V1.0
 * @ClassName SchedualServiceHystric
 * @Description 服务降级
 * @Date 2020-01-15 11:35:00
 * 说明：在一个分布式系统中，当访问高峰期或资源有限时，需要关掉某个服务，
 * 若有请求访问该服务，不能因为系统服务关掉了，就一直中断在该调用服务处，
 * 这时就需要请求返回指定的错误信息。例如在分布式系统中有A、B两个服务，
 * 因为资源有限，需要关掉B服务，A服务在调用B服务时，没有调通，
 * 此时A返回指定的错误信息，注意不是在B服务端返回的，是A客户端返回的错误信息
 **/
@Component
public class SchedualServiceHystric implements FallbackFactory<UserService> {

    @Override
    public UserService create(Throwable throwable) {
        return new UserService() {
            @Override
            public RetResult doLogin(String username, String password) {

                return RetResponse.makeOkMsg("server is abort !!!!");
            }
        };
    }
}
