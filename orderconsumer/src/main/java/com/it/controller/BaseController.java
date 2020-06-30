package com.it.controller;

import com.alibaba.fastjson.JSONObject;
import com.it.entity.Person;
import com.it.service.HelloRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/base")
public class BaseController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    HelloRemote helloRemote;

    @RequestMapping(value = "/getPersonList")
    @ResponseBody
    public List<Person> getPersonList(){
        List<Person> personList = helloRemote.getPersonList();
        System.out.println(personList);
        return personList;
    }

    @RequestMapping(value = "/hello")
    @ResponseBody
    public String hello(@RequestParam(value = "name") String name){
        String result = helloRemote.hello(name);
        System.out.println(result);
        return result;
    }


    @RequestMapping(value = "/hi")
    @ResponseBody
    public String hi(@RequestParam String name) {
        String result = restTemplate.getForObject("http://SERVICE-HI/hi?name=" + name, String.class);
        System.out.println(result);
        return result;
    }

    @RequestMapping(value = "/api/person/{id}" ,method = RequestMethod.GET)
    public ResponseEntity<Map> findAll(@PathVariable("id") Integer id){
        ResponseEntity<Map> person = restTemplate.getForEntity("http://SERVICE-PROVIDE/api/person/"+id, Map.class);
        return person;
    }

    @RequestMapping(value = "/api/person" ,method = RequestMethod.POST)
    public ResponseEntity<String> findAll(@RequestBody Person person){
        HttpHeaders headers = new HttpHeaders();
        headers.add("api-version","1.0");
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("personName","test");
        requestBody.add("personGender","male");
        requestBody.add("description","test");
        HttpEntity<MultiValueMap> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity("http://SERVICE-PROVIDE/api/person", requestEntity, String.class);
        return stringResponseEntity;
    }
}
