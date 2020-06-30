package com.it.utils.lambda;

import com.google.common.collect.Sets;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class ListLamb {

    public static void main(String[] args) {
        Set<Account> treeSet = Sets.newHashSet();
        Account account = new Account(1L,"BT场景","自动分析");
        Account account1 = new Account(2L,"WIFI场景","自动分析");
        Account account2 = new Account(1L,"BT场景","自动分析");

        treeSet.add(account);
        treeSet.add(account1);
        treeSet.add(account2);
        ListLamb.build().test005(treeSet);
    }

    public void test005(Set<Account> treeSet){
        List<Account> collect = treeSet.stream().distinct().collect(Collectors.toList());
        System.out.println(collect);
    }

    /**
     * 将集合中的数据按照某个属性求和/是否含有某个值
     * @param treeSet
     */
    public void test004(Set<Account> treeSet){
        long sum = treeSet.stream().mapToLong(Account::getId).sum();
        boolean flag = treeSet.stream().anyMatch(account -> account.getUserName().equals("BT场景"));
        System.out.println(flag);

        System.out.println(sum);
    }

    /**
     *  根据条件过滤list
     * @param treeSet
     */
    public void test003(Set<Account> treeSet){
        Set<Account> filterSet = treeSet.stream()
                .filter(account ->
                        account.getUserName().equals("BT场景")
                ).collect(Collectors.toSet());
        System.out.println(filterSet.toString());
    }

    /**
     * list分组
     * @param treeSet
     */
    public void test002(Set<Account> treeSet){
        Map<String, List<Account>> collect = treeSet.
                stream().
                collect(Collectors.groupingBy(Account::getPermission));
        System.out.println(collect);
    }

    /**
     * list 转 map
     */
    public void test001(Set<Account> treeSet){
        Map<Long, Account> collect = treeSet.stream().collect(Collectors.toMap(Account::getId, a -> a, (k1, k2) -> k1));
        Map<Long, Account> collect1 = treeSet.stream().collect(Collectors.toMap(Account::getId, account -> account));
        System.out.println(collect1.toString());
    }

    public static ListLamb build(){
        return new ListLamb();
    }
}
