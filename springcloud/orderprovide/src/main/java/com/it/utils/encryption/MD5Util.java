package com.it.utils.encryption;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author 18576756475
 * @version V1.0
 * @ClassName MD5Util
 * @Description TODO
 * @Date 2019-12-05 9:44:10
 **/
public abstract class MD5Util {

    /**
     * MD5加密
     * @param data
     *  待加密数据
     * @return byte[] 消息摘要
     * @throws Exception
     */
    public static byte[] encodeMD5(String data) throws Exception {

        // 执行消息摘要
        return DigestUtils.md5(data);
    }

    /**
     * MD5加密
     *
     * @param data
     * 待加密数据
     * @return byte[] 消息摘要
     *
     * @throws Exception
     */
    public static String encodeMD5Hex(String data) {
        // 执行消息摘要
        return DigestUtils.md5Hex(data);
    }
}
