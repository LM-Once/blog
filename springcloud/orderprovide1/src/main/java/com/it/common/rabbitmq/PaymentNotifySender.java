package com.it.common.rabbitmq;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentNotifySender {

    protected static Logger logger= LoggerFactory.getLogger(PaymentNotifySender.class);

    /*@Autowired
    private AmqpTemplate amqpTemplate;

    public void snder(String msg){
        logger.info("to notify.payment send message: "+msg);
        amqpTemplate.convertAndSend("notify.payment",msg);
    }*/
}
