package com.it;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@EnableEurekaClient
@RestController
@EnableJms
public class ProvideApplication {

    protected static Logger logger= LoggerFactory.getLogger(ProvideApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ProvideApplication.class, args);
        logger.info("SpringBoot Start Success");
    }

}
