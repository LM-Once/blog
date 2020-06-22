package com.it.utils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * @author 18576756475
 * @version V1.0
 * @ClassName PropertyUtils
 * @Description 读取属性文件
 * @Date 2019-12-06 11:45:12
 **/
public class PropertyUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyUtils.class);

    /**
     * created by 18576756475
     * parameter 文件路径
     * desc  返回Properties 用于取得本地配置变量
     */
    public static Properties getProperties(String path) {
        Properties propsload = new Properties();
        FileInputStream iFile = null;
        try {
            iFile = new FileInputStream(path);
            propsload.load(iFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return propsload;
    }

    /**
     * 过滤不拦截的接口 字符串
     *
     * @param path 文件路径
     * @return
     */
    public static Set<String> getPropertiesValue(String path) {
        Properties properties = getProperties(path);
        Enumeration<?> enumeration = properties.elements();

        Set<String> filterIntList = Sets.newHashSet();
        while (enumeration.hasMoreElements()) {
            String value = (String) enumeration.nextElement();
            filterIntList.add(value);
        }
        return filterIntList;
    }

    /**
     * 获取配置文件 key-value
     *
     * @return
     */
    public static TreeMap<String, Object> getProKeyValue(String proUrl) {
        LOGGER.info("log - > read property file : " + proUrl);
        Properties properties = getProperties(proUrl);
        Iterator<Map.Entry<Object, Object>> iterator = properties.entrySet().iterator();
        TreeMap<String, Object> keyValues = keyValues = Maps.newTreeMap();
        while (iterator.hasNext()) {
            Map.Entry<Object, Object> entry = iterator.next();
            String key = (String) entry.getKey();
            Object value = entry.getValue();
            keyValues.put(key, value);
        }
        LOGGER.info("log - > read result : " + JSONObject.toJSONString(keyValues));
        return keyValues;
    }

    public static void main(String[] args) {
        System.out.println(2020-1024);
    }
}
