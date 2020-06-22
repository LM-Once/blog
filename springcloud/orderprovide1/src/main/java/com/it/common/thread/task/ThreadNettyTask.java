package com.it.common.thread.task;

import com.it.init.StartClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 18576756475
 * @version V1.0
 * @ClassName ThreadNettyTask
 * @Description 执行netty 开启任务
 * @Date 2019-12-13 9:05:10
 **/
public class ThreadNettyTask implements Runnable{

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadNettyTask.class);

    private int port;

    public ThreadNettyTask(int port){
        this.port = port;
    }

    @Override
    public void run() {
        LOGGER.info("start netty server, port is " + port);
        StartClass.startNettyServer(port);
    }
}
