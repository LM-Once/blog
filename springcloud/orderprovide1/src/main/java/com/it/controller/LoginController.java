package com.it.controller;

import com.it.common.RetResponse;
import com.it.common.RetResult;
import com.it.common.activemq.MqProducer;
import com.it.common.constant.ErrorCode;
import com.it.common.constant.UserConstant;
import com.it.common.redis.RedisUtils;
import com.it.domain.User;
import com.it.service.UserService;
import com.it.utils.encryption.MD5Util;
import com.it.utils.token.TokenTools;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;

/**
 * @author 18576756475
 * @version V1.0
 * @ClassName 18576756475
 * @Description login controller
 * @Date 2019-12-05 9:30:00
 **/
@RestController
@RequestMapping("api/login")
public class LoginController extends BaseAction{

    private static Logger logger = Logger.getLogger(LoginController.class);

    @Autowired
    UserService userService;

    @Autowired
    MqProducer mqProducer;

    @Autowired
    RedisUtils redisUtils;

    /**
     * 登录接口
     * @param username 用户账号
     * @param password 用户密码
     * @return 用户信息
     */
    @RequestMapping(value = "doLogin",method = RequestMethod.POST)
    public RetResult doLgoin(String username,String password){

        logger.info("{}: 提供者端口-> 9996");
        if (StringUtils.isBlank(username)){
            return RetResponse.makeOkMsg(ErrorCode.USER_NOT_NULL.getErrorMsg());
        }
        if (StringUtils.isBlank(password)){
            return RetResponse.makeOkMsg(ErrorCode.PASSWORD_NOT_NULL.getErrorMsg());
        }
        String encryptionPassword = MD5Util.encodeMD5Hex(password);
        User user = userService.queryUserInfo(username, encryptionPassword);
        if (null == user){
            return RetResponse.makeOkMsg(ErrorCode.USERNAME_PASSWORD_ERROR.getErrorMsg());
        }
        String tokenServer = TokenTools.createToken();
        //生成token存入user对象
        user.setToken(tokenServer);
        HttpSession session = request.getSession();
        session.setAttribute(UserConstant.SESSION_KEY,user);
        session.setMaxInactiveInterval(UserConstant.SESSION_EFFECTIVE_TIME);
        //mqProducer.sendMessage("测试信息");

        //缓存
        //redisUtils.set(user.getId(), user);
        return RetResponse.makeOkData(user);
    }

    /**
     *  从缓存拿取用户信息
     * @param id 用户id
     * @return 返回用户信息
     */
    @RequestMapping(value = "getUserInfo", method = RequestMethod.POST)
    public RetResult getUserInfo (String id){

        Object user = redisUtils.get(id);
        if(user == null ){
            logger.info("");
        }
        logger.info("从缓存获取的数据 ：" +user.toString());
        return RetResponse.makeOkData(user);
    }
}