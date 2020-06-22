package com.it.test;

import com.google.common.collect.Sets;
import java.util.*;


/**
 * @author 18576756475
 * @version V1.0
 * @ClassName Collection
 * @Description 集合类
 * @Date 2019-12-19 14:38:00
 **/
public class Collection {

    private int count = 9700;

    public static void main(String[] args) {
        int mo = 11;
        mo += 4;
        mo += 4;
        mo += 2;
        System.out.println(mo);
    }

    /**
     * characteristic :
     *  1. 不能保证元素的顺序
     *  2. 元素值可以使空
     *  3. 线程不同步
     */
    public static void setTest(){
        Set<Object> set = Sets.newHashSet();
        set.add("脚本用例1");
        set.add("脚本用例2");
        set.add("脚本用例1");
        set.forEach(obj->{
            System.out.println(obj);
        });
       /* set.removeIf(obj->obj.equals("脚本用例1"));
        System.out.println(set.size());*/
        Iterator<Object> it = set.iterator();
        while (it.hasNext()){
            System.out.println(it.next());
        }
        String[] setArray = set.toArray(new String[set.size()]);
        System.out.println(setArray.length);
    }

    public static void treeSetTest(){
        Set<Object> treeSet = new TreeSet<>();
        treeSet.add("用例1");
        treeSet.add("用例2");
        treeSet.add("用例1");
        Iterator<Object> it = treeSet.iterator();
        while (it.hasNext()){
            System.out.println(it.next());
        }
        treeSet.stream().filter(Objects::nonNull);
        treeSet.stream().forEach(obj -> System.out.println(obj));
    }
}
