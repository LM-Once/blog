package com.it.init;

import com.it.common.netty.server.NettyServer;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 18576756475
 * @version V1.0
 * @ClassName StartClass
 * @Description 启动netty 服务
 * @Date 2019-12-12 10:32:25
 **/
public class StartClass {

    private static final Logger LOGGER = LoggerFactory.getLogger(StartClass.class);
    /**
     * 启动netty 服务
     * @param port
     */
    public static void startNettyServer(int port){

        NettyServer.getInstance(port).run();
    }

    /**
     *  启动activeMq
     * @param queue 队列名
     * @return
     */
    public static ActiveMQQueue startActiveMq(String queue){
        LOGGER.info("log -> 启动ActiveMq queue{}: "+queue);
        return new ActiveMQQueue(queue);
    }
}
