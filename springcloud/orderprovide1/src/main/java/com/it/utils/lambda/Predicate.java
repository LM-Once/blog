package com.it.utils.lambda;

public class Predicate<T> {

    public boolean test(Account account){
        if (account.getId()>3000){
            return true;
        }
        return false;
    }
}
