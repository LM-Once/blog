package com.it.test;

import com.google.common.collect.Lists;
import com.it.domain.Person;
import com.it.domain.User;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author 18576756475
 * @version V1.0
 * @ClassName StreamApi
 * @Description 流的应用
 * 步骤1：创建stream
 * 步骤2：中间操作
 * 步骤3：终止操作
 * @Date 2019-12-31 11:23:00
 **/
public class StreamApi {

    /**
     * stream 的创建
     */
    public void getStream() {
        List<String> list = Arrays.asList();
        Stream<String> stream = list.parallelStream();

        String[] str = new String[10];
        Stream<String> stream1 = Arrays.stream(str);

        Stream<String> stream3 = Stream.of("");

        //创建无限流
        // 迭代
        Stream<Integer> stream4 = Stream.iterate(0, (x) -> x + 2);

        //生成
        Stream<Double> stream5 = Stream.generate(() -> Math.random());
    }

    /**
     * stream 流的中间操作
     */
    public void doOperation() {
        List<Integer> ageList = Lists.newArrayList();
        ageList.add(15);
        ageList.add(14);
        ageList.add(17);
        ageList.add(16);
        ageList.parallelStream().filter(age -> age >= 15).limit(2).distinct().forEach(System.out::println);

        ArrayList<Person> personList = Lists.newArrayList();
        personList.add(new Person().setId(2).setPersonName("用例2").setPersonGender("male"));
        personList.add(new Person().setId(3).setPersonName("用例3").setPersonGender("male"));
        personList.add(new Person().setId(4).setPersonName("用例4").setPersonGender("female"));
        personList.add(new Person().setId(3).setPersonName("用例5").setPersonGender("female"));

        personList.stream().sorted((a1, a2) -> {
            if (a1.getId().equals(a2.getId())) {
                return a1.getPersonName().compareTo(a2.getPersonName());
            } else {
                return a1.getId().compareTo(a2.getId());
            }
        }).forEach(System.out::println);
    }

    /**
     * stream流的终止操作
     */
    public void endOperation() {
        ArrayList<Person> personList = Lists.newArrayList();
        personList.add(new Person().setId(2).setPersonName("用例2").setPersonGender("male"));
        personList.add(new Person().setId(3).setPersonName("用例3").setPersonGender("male"));
        personList.add(new Person().setId(4).setPersonName("用例4").setPersonGender("female"));
        personList.add(new Person().setId(3).setPersonName("用例5").setPersonGender("female"));

        System.out.println(personList.parallelStream().allMatch((gen) -> gen.getPersonGender().equals("male")));
        System.out.println(personList.parallelStream().anyMatch((gen) -> gen.getPersonGender().equals("male")));
        System.out.println(personList.parallelStream().noneMatch((gen) -> gen.getPersonGender().equals("male")));
        System.out.println(personList.parallelStream().findFirst().get());
        System.out.println(personList.parallelStream().findAny().get());
        System.out.println(personList.parallelStream().count());
        System.out.println(personList.parallelStream().max(Comparator.comparingInt(Person::getId)).get());
        System.out.println(personList.parallelStream().min(Comparator.comparingInt(Person::getId)).get());
    }

    /**
     * reduce: 规约操作
     */
    public void reduce(List<Person> personList) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Integer count2 = list.stream()
                .reduce(0, (x, y) -> x + y);
        System.out.println(count2);

        Optional<Integer> sum = personList.stream()
                .map(Person::getId)
                .reduce(Integer::sum);
        System.out.println(sum);
    }

    /**
     * collect 收集操作
     * @param personList
     */
    public void collect(List<Person> personList){
        List<Integer> collects = personList.parallelStream().map(Person::getId).collect(Collectors.toList());
        collects.forEach(System.out::println);
    }

    public static void main(String[] args) {
        ArrayList<Person> personList = Lists.newArrayList();
        personList.add(new Person().setId(2).setPersonName("用例2").setPersonGender("male"));
        personList.add(new Person().setId(3).setPersonName("用例3").setPersonGender("male"));
        personList.add(new Person().setId(4).setPersonName("用例4").setPersonGender("female"));
        personList.add(new Person().setId(3).setPersonName("用例5").setPersonGender("female"));
        new StreamApi().collect(personList);
    }
}
