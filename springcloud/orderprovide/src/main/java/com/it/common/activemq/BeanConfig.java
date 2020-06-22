package com.it.common.activemq;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 18576756475
 * @version V1.0
 * @ClassName BeanConfig
 * @Description TODO
 * @Date 2019-12-30 10:13:00
 **/
@Configuration
public class BeanConfig {

    private static Logger LOGGER = LoggerFactory.getLogger(BeanConfig.class);

    @Bean
    public ActiveMQTopic SPECIAL_TOPIC(){
        LOGGER.info("create activemq SPECIAL_TOPIC");
        return new ActiveMQTopic("SPECIAL_TOPIC");
    }

    @Bean
    public ActiveMQQueue queue() {
        LOGGER.info("create activemq special queue");
        return new ActiveMQQueue("special");
    }

    @Bean
    public ActiveMQQueue queue1() {
        LOGGER.info("create activemq special queue");
        return new ActiveMQQueue("special1");
    }
}
