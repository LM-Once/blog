package com.it.utils.lambda;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;


/**
 * 方法引用
 */
public class MethodReference {

    /**
     * 数组引用
     */
    public void ArrayReference(){
        Function<Integer, String[]> function = a -> new String[a];
        String[] apply = function.apply(5);
        System.out.println(apply.length);

        Function<Integer, String[]> function1 = String[]::new;
        String[] apply1 = function1.apply(3);
        System.out.println(apply1.length);
    }
    /**
     * 对象的引用 :: 实例方法名
     */
    public void test02() {
        PrintStream ps = System.out;
        Consumer<String> consumer = (a) -> ps.println(a);
        consumer.accept("aaaaaaa");
        System.out.println("等同于");

        Consumer<String> consumer1 = ps::println;
        consumer1.accept("对象的引用");
    }

    /**
     * 对象的引用 :: 实例方法名
     */
    public void test03() {
        Account emp = new Account(101l, "张三", "操作权限");
        Supplier<String> sup = () -> emp.getUserName();
        System.out.println(sup.get());

        System.out.println("----------------------------------");

        Supplier<String> sup1 = emp::getUserName;
        System.out.println(sup1.get());

    }

    //类名 :: 静态方法名
    public void test4() {
        Comparator<Integer> com = (x, y) -> Integer.compare(x, y);
        System.out.println("-------------------------------------");

        Comparator<Integer> com2 = Integer::compare;
    }

    //类名 :: 实例方法名
    public void test5() {
        BiPredicate<String, String> bp = (x, y) -> x.equals(y);
        System.out.println(bp.test("abcde", "abcde"));

        System.out.println("-----------------------------------------");

        BiPredicate<String, String> bp2 = String::equals;
        System.out.println(bp2.test("abc", "abc"));

        System.out.println("-----------------------------------------");

    }



    public static void main(String[] args) {
        Account emp1 = new Account(101l, "张三", "操作权限");
        Account emp2 = new Account(102l, "李四", "操作权限");
        ArrayList<Account> accounts = Lists.newArrayList();
        accounts.add(emp1);
        accounts.add(emp2);

        List<String> collect = accounts.stream().map(Account::getUserName).collect(Collectors.toList());
        System.out.println(collect.toString());
    }
}
