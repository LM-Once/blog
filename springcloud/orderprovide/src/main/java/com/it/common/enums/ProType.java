package com.it.common.enums;

/**
 * @author 18576756475
 * @version V1.0
 * @ClassName ProType
 * @Description 属性键
 * @Date 2019-12-12 11:22:22
 **/
public enum ProType {

    HOST("HOST"),
    PORT("PORT"),
    ACTIVEMQ_QUEUE_SPECIAL("ACTIVEMQ_QUEUE_SPECIAL");

    private String key;

    ProType(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }}
