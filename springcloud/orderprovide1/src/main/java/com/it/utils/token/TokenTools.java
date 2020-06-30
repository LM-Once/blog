package com.it.utils.token;

import com.alibaba.druid.util.StringUtils;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 18576756475
 * @version V1.0
 * @ClassName TokenTools
 * @Description Token的工具类
 * @Date 2019-12-05 12:12:54
 **/
public class TokenTools {

    /**
     * 生成token放入session
     * @param
     */
    public static String createToken(){
        String token = TokenProccessor.getInstance().makeToken();
        return token;
    }

    /**
     * 移除token
     * @param request
     * @param tokenServerkey
     */
    public static void removeToken(HttpServletRequest request,String tokenServerkey){
        request.getSession().removeAttribute(tokenServerkey);
    }

    /**
     * 判断请求参数中的token是否和session中一致
     * @param request
     * @param tokenClientkey
     * @param tokenServerkey
     * @return
     */
    public static boolean judgeTokenIsEqual(HttpServletRequest request,
                                            String tokenClientkey,String tokenServerkey){
        String token_client = request.getParameter(tokenClientkey);
        if(StringUtils.isEmpty(token_client)){
            return false;
        }
        String token_server = (String) request.getSession().getAttribute(tokenServerkey);
        if(StringUtils.isEmpty(token_server)){
            return false;
        }
        if(!token_server.equals(token_client)){
            return false;
        }
        return true;
    }
}
