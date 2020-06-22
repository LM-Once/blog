package com.it.common.activemq;

import com.it.ProvideApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MqConsumer {

    protected static Logger logger= LoggerFactory.getLogger(ProvideApplication.class);

    /**
     * 客户端消费
     * @param consumer
     */
    @JmsListener(destination = "special")
    public void receiveQueue(String consumer) {
        logger.info("生产者提供的数据："+consumer);
        logger.info( consumer + "log模块测试用例。");
    }

    /**
     * 客户端消费
     * @param consumer
     */
    @JmsListener(destination = "special1")
    public void receiveQueue1(String consumer) {
        logger.info("生产者提供的数据："+consumer);
        System.out.println( consumer + "log模块测试用例。");
    }
}
