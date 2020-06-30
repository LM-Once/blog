package com.it.common.activemq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;


@Component
public class MqProducer {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;


    public void sendMessage(String data) {
        this.jmsMessagingTemplate.convertAndSend(this.queue, data);
    }

}
