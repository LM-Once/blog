package com.it.controller.Interceptor;

import com.alibaba.fastjson.JSON;
import com.it.common.RetResponse;
import com.it.common.RetResult;
import com.it.common.constant.ErrorCode;
import com.it.common.constant.UserConstant;
import com.it.domain.Person;
import com.it.domain.User;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;


/**
 * 登录拦截器
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginInterceptor.class);

    /*
     *  添加白名单
     */
    public static final String[] ALLOW_DOMAIN = {"http://172.17.238.88:8080",
            "http://172.17.53.129:8080","http://localhost:8080","http://172.17.238.127:8081"};

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object o) throws Exception {
        String requestURI = request.getRequestURI();
        LOGGER.info("request url ：" +requestURI);

        String token = request.getHeader("h-token");
        /*if (null == token){
            LOGGER.error("no token error");
            throw new Exception(ErrorCode.NO_TOKEN_ERROR.getErrorMsg());
        }

        User user =(User) request.getSession().getAttribute(UserConstant.SESSION_KEY);
        if (null == user){
            LOGGER.info("user , user{}", user);
            RetResult retResult = RetResponse.makeNoLogin();
            reLogin(response,retResult,request);
        }
        if (!user.getToken().equals(token)){
            LOGGER.info("error token, token{}",token);
        }*/
        return true;
    }

    private boolean reLogin(HttpServletResponse response, RetResult retResult, HttpServletRequest request) throws IOException {
        try {
            String originHeader = request.getHeader("Origin");
            if (!StringUtils.isBlank(originHeader)) {
                if (Arrays.asList(ALLOW_DOMAIN).contains(originHeader)) {
                    response.setHeader("Access-Control-Allow-Origin", originHeader);
                    response.setHeader("Access-Control-Allow-Credentials", "true");
                    response.setHeader("Access-Control-Allow-Methods", "POST, GET, PATCH, DELETE, PUT");
                    response.setHeader("Access-Control-Max-Age", "3600");
                    response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
                }
            }
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(retResult));
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500);
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        LOGGER.info("login interceptor postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        LOGGER.info("login interceptor afterCompletion");
    }
}
