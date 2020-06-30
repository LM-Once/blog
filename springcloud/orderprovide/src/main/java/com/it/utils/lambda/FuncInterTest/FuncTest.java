package com.it.utils.lambda.FuncInterTest;

import com.google.common.collect.Sets;
import com.it.utils.lambda.Account;
import java.util.Set;
import java.util.stream.Collectors;


public class FuncTest {

    public Set<Account> filterAccount(Set<Account> accountSet,
                                      BiFuncInter<Set<Account>,Set<Account>> biFuncInter){
        return biFuncInter.apply(accountSet);
    }

    public void filterAfterAccount(Set<Account> accountSet){
        Set<Account> accounts = filterAccount(accountSet,
                new BiFuncInter<Set<Account>, Set<Account>>() {
            @Override
            public Set<Account> apply(Set<Account> accountSet) {
                return accountSet.stream().filter(account -> account.getId() > 5).collect(Collectors.toSet());
            }
        });
        System.out.println(accounts);
    }

    public static FuncTest build(){
        return new FuncTest();
    }

    public static void main(String[] args) {
        Set<Account> treeSet = Sets.newHashSet();
        Account account = new Account(1L,"BT场景","自动分析");
        Account account1 = new Account(2L,"WIFI场景","自动分析");
        Account account2 = new Account(6L,"BT场景","自动分析");
        treeSet.add(account);
        treeSet.add(account1);
        treeSet.add(account2);
        FuncTest.build().filterAfterAccount(treeSet);
    }
}
