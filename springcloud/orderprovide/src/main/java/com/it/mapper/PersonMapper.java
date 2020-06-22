package com.it.mapper;

import com.it.domain.Person;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PersonMapper {

    List<Person> findAll();

    Person findOne(@Param("id") Integer id);

    int savePerson(Person person);

    int updatePerson(Person person);

    int deletePerson(int id);

}
