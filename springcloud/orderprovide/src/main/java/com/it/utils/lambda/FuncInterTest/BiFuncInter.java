package com.it.utils.lambda.FuncInterTest;

@FunctionalInterface
public interface BiFuncInter<T,R> {

    R apply(T t);
}
