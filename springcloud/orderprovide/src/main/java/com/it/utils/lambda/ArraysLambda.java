package com.it.utils.lambda;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ArraysLambda {

    public static void main(String[] args) {
        //多线程情况下使用vector，他们基本一致，vector通过同步关键字修饰。
        //vector是ArrayList的多线程替代品
        ArrayList<String> logModels = Lists.newArrayList();
        logModels.add("BT场景");
        logModels.add("WIFI场景");
        logModels.add("BT场景");
        LinkedList<Object> objects = Lists.newLinkedList();
        objects.add("BT场景");
        objects.add("WIFI场景");
        objects.add("BT场景");
        Iterator<Object> iterator1 = objects.iterator();
        while (iterator1.hasNext()){
            System.out.println(iterator1.next());
        }

        Iterator<String> iterator = logModels.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
        List<String> bt场景1 = logModels.stream().filter((String model) -> !model.contains("BT场景1")).collect(Collectors.toList());
        System.out.println(bt场景1.toString());
    }
}
