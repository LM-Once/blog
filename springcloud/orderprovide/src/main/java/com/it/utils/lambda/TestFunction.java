package com.it.utils.lambda;

import com.google.common.collect.Sets;
import java.util.Set;

public class TestFunction {

    public String getResult(LambdaFunction function){
        Set<String> accountSet = Sets.newHashSet();
        accountSet.add("BT场景");
        accountSet.add("WIFI场景");
        String jsonAccoutSet = function.toString(accountSet);
        return jsonAccoutSet;
    }

    public void test(){
        String result = getResult(new LambdaFunction() {
            @Override
            public String toString(Set<String> accountSet) {
                return accountSet.toString();
            }
        });
        System.out.println(result);
    }
    public void testLambda(){
        String result = getResult(accountSet -> {
            return accountSet.toString();
        });
        System.out.println(result);
    }

    public static TestFunction build(){
        return new TestFunction();
    }

    public static void main(String[] args) {
        TestFunction.build().testLambda();
    }
}
