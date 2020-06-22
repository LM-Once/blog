package com.it.common.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
//@RabbitListener(queues = "notify.payment")
public class PaymentNotifyReceive {

    /*protected static Logger logger= LoggerFactory.getLogger(PaymentNotifyReceive.class);

    @RabbitHandler
    public void receive(String msg){
        logger.info("notify.payment receive message: "+msg);
    }*/

}
