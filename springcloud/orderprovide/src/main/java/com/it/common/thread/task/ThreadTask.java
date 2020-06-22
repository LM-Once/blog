package com.it.common.thread.task;


import com.it.common.thread.ThreadPoolManager;
import org.apache.log4j.Logger;

/**
 * @author 18576756475
 * @version V1.0
 * @ClassName ThreadTest
 * @Description 测试 线程任务
 * @Date 2019-12-11 14:30:30
 **/
public class ThreadTask implements Runnable{

    private static final Logger LOGGER = Logger.getLogger(ThreadTask.class);
    private int taskNum;

    public ThreadTask(int num) {
        this.taskNum = num;
    }

    @Override
    public void run() {

        LOGGER.info("正在执行task :" +taskNum);
        try {
            Thread.currentThread().sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOGGER.info("task "+ taskNum + "执行完毕");
    }

    public static void main(String[] args) {
        ThreadPoolManager sInstance = ThreadPoolManager.getInstance();
        for (int i=0; i<25; i++){
            ThreadTask tTask = new ThreadTask(i);
            sInstance.execute(tTask);
            LOGGER.info("线程池中线程数目：" +sInstance.getPoolSize());
            LOGGER.info("队列中等待执行的任务数：" +sInstance.getPoolSize());
            LOGGER.info("已执行完别的任务数：" +sInstance.getPoolSize());
        }
    }
}
