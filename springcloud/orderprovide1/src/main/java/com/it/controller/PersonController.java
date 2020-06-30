package com.it.controller;

import com.alibaba.fastjson.JSON;
import com.it.common.activemq.MqProducer;
import com.it.domain.Person;
import com.it.service.PersonService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


@RestController
public class PersonController extends BaseAction {

    @Autowired
    private PersonService personService;

    @Autowired
    MqProducer mqProducer;


    @RequestMapping(value = "hello")
    public String helloWorld(String name) {
        System.out.println(name);
        mqProducer.sendMessage("22222");
        return name + "--SUCCESS";
    }

    @RequestMapping(value = "/api/person", method = RequestMethod.GET)
    public List<Person> findAll() {
        return personService.findAll();
    }

    @RequestMapping(value = "/api/person/{id}", method = RequestMethod.GET)
    public Person findOne(@PathVariable("id") Integer id) {
        Person per = personService.findOne(id);
        String strPerson = JSON.toJSONString(per);
        mqProducer.sendMessage(strPerson);
        return personService.findOne(id);
    }

    /**
     * the city is a fine place
     * and worth fighting for
     * i agree with the second part.
     * @param person
     */
    @RequestMapping(value = "/api/person", method = RequestMethod.POST)
    public void savePerson(@RequestBody Person person) {
        personService.savePerson(person);
    }

    @RequestMapping(value = "/api/person", method = RequestMethod.PUT)
    public void updatePerson(@RequestBody Person person) {
        personService.updatePerson(person);
    }

    @RequestMapping(value = "/api/person/{id}", method = RequestMethod.DELETE)
    public void deletePerson(@PathVariable("id") Integer id) {
        personService.deletePerson(id);
    }

    public static void writeDataFile (File file , String data){
        try {
            if (!file.exists()){
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data.getBytes("utf-8"));
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //paigutang
        String cailiao = "paigu + shanyao + yumi +huluobo + hongzao";
        String buzhou1 = "paigu+shui+liaojiu"; //quxing
        String buzhou2 = "paigu + jiangpian "; //xiaochao 2~3min
    }
}
