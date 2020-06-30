package com.it.utils.lambda;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class FilterEmpSalary {

    public List<Account> filterAccount(List<Account> accounts, Predicate<Account> predicate){
        ArrayList<Account> filterAccount = Lists.newArrayList();
        for (Account account:accounts){
            if (predicate.test(account)){
                filterAccount.add(account);
            }
        }
        return filterAccount;
    }

    public static void main(String[] args) {
        List<Account> accounts = Lists.newArrayList();
        Predicate<Account> predicate = new Predicate();
        new FilterEmpSalary().filterAccount(accounts, predicate);
    }
}
