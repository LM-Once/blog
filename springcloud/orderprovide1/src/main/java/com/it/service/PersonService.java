package com.it.service;

import com.it.domain.Person;
import com.it.mapper.PersonMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    PersonMapper personMapper;

    public List<Person> findAll(){
        return personMapper.findAll();
    }

    public Person findOne(Integer id){
        return personMapper.findOne(id);
    }

    public int savePerson(Person person){
        return personMapper.savePerson(person);
    }

    public int updatePerson(Person person){
        return personMapper.updatePerson(person);
    }

    public int deletePerson(int id){
        return personMapper.deletePerson(id);
    }
}
