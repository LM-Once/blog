package com.it.utils.lambda;

import java.util.HashMap;
import java.util.Map;


public class MapLambda {

    public static void main(String[] args) {

        Map<String,Object> items = new HashMap<>();

        items.put("1","BT场景");
        items.put("2","WIFI场景");
        for (Map.Entry<String,Object> item:items.entrySet()){
            System.out.println(item.getKey());
            System.out.println(item.getValue());
        }

        items.forEach((key,value)->{
            System.out.println(key+","+value);
        });
    }
}
