package com.it.rtc_example;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Utils {

    /**
     * md5 公共方法
     * @param src
     * @return
     */
    public static String md5(String src) {
        if (src == null) {
            return null;
        }
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md5.digest(src.getBytes("UTF-8"));
            StringBuilder builder = new StringBuilder();
            for (byte c : md5Bytes) {
                int i = c & 0xff;
                if (i < 16) {
                    builder.append(0);
                }
                builder.append(Integer.toHexString(i));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            // do nothing
        } catch (UnsupportedEncodingException e) {
            // do nothing
        }
        return null;
    }
}
