package com.it.common;

/**
 * 响应工具类
 * @param <T>
 */
public class RetResponse<T> {

    /***
     * 响应成功，无返回结果
     * @param <T>
     * @return
     */
    public static <T> RetResult<T> makeOk(){
        return new RetResult<T>().setCode(RetCode.SUCCCESS.code)
                .setSmg(RetCode.SUCCCESS.msg);
    }

    /**
     * 响应成功，有返回结果
     * @param data
     * @return
     */
    public static <T> RetResult makeOkData(T data){
        return new RetResult<T>().setCode(RetCode.SUCCCESS.code)
                .setSmg(RetCode.SUCCCESS.msg)
                .setData(data);
    }

    /**
     * 响应失败
     * @param <T>
     * @return
     */
    public static <T> RetResult makeFail(){
        return new RetResult<T>().setCode(RetCode.FAIL.code)
                .setSmg(RetCode.FAIL.msg);
    }

    /**
     * 响应失败，参数有误
     * @param <T>
     * @return
     */
    public static <T> RetResult makeErrParam(){
        return new RetResult<T>().setCode(RetCode.ERRORPARAM.code)
                .setSmg(RetCode.ERRORPARAM.msg);
    }
    /**
     * 响应OK
     * @param <T>
     * @return
     */
    public static <T> RetResult makeOkMsg(String msg){
        return new RetResult<T>().setCode(RetCode.ERRORPARAM.code)
                .setSmg(msg);
    }
    /**
     * 响应OK
     * @param <T>
     * @return
     */
    public static <T> RetResult makeNoLogin(){
        return new RetResult<T>().setCode(RetCode.NO_LOGIN.code)
                .setSmg(RetCode.NO_LOGIN.msg);
    }
}
