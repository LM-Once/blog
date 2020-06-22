package com.it.common.thread;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;

import java.util.concurrent.*;

/**
 * @author 18576756475
 * @version V1.0
 * @ClassName ThreadPoolUtils
 * @Description 线程池工具类
 * @Date 2019-12-11 11:12:12
 **/
public class ThreadPoolManager<T> {

    /**
     * 根据cpu数量动态的配置核心线程数和最大线程数
     */
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    /**
     * 核心线程数=核心线程数+1
     */
    private static final int CORE_POOL_SIZE = CPU_COUNT +1;
    /**
     * 线程池最大线程数= cpu核心数*2 +1
     */
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    /**
     * 非核心线程闲置时间超时1秒
     */
    private static final int KEEP_ALIVE = 1;
    /**
     * 线程池对象
     */
    private ThreadPoolExecutor executor;


    private static ThreadPoolManager sInstance;

    /**
     * 采用单例模式
     * 避免产生过多对象消费资源
     */
    private ThreadPoolManager(){

    }

    public synchronized static ThreadPoolManager getInstance(){
        if (sInstance == null){
            sInstance = new ThreadPoolManager();
        }
        return sInstance;
    }

    /**
     * 开启一个无返回结果的线程
     * @param run
     */
    public void execute(Runnable run){
        if (executor == null){
            /**
             * corePoolSize 核心线程数
             * maximumPoolSize 线程池所能容纳的最大线程数(workQueue队列满了之后才会开启)
             * keepAliveTime 非核心线程闲置时间超时时长
             * unit: keepAliveTime的单位
             * workQueue: 等待队列，存储还未执行的任务
             * threadFactory: 线程创建的工厂
             * handler: 异常处理机制
             */
            executor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE,
                    TimeUnit.SECONDS, new ArrayBlockingQueue<>(20), Executors.defaultThreadFactory(),
                    new ThreadPoolExecutor.AbortPolicy());
        }
        // 把一个任务丢到了线程池中
        executor.execute(run);
    }

    /**
     *  开启一个有返回结果的线程
     * @param callable 线程
     * @return
     */
    public Future<T> submit(Callable<T> callable){
        if (executor == null){
            /**
             * corePoolSize: 核心线程数
             * maximumPoolSize: 线程池所能容纳的最大线程数(workQueue队列满了之后才会开启)
             * keepAliveTime: 非核心线程闲置时间超时时长
             * unit：keepAliveTime的单位
             * workQueue: 等待队列，存储还未执行的任务
             * threadFatory: 线程创建工厂
             * handler: 异常处理机制
             */
            executor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE,
                    TimeUnit.SECONDS, new ArrayBlockingQueue<>(20),Executors.defaultThreadFactory(),
                    new ThreadPoolExecutor.AbortPolicy());
        }
        return executor.submit(callable);
    }

    /**
     * 把任务移除等待队列
     * @param run
     */
    public void cancel(Runnable run){
        if ( run != null){
            executor.getQueue().remove(run);
        }
    }

    /**
     *返回线程池中线程数
     * @return
     */
    public int getPoolSize(){

        return executor.getPoolSize();
    }

    /**
     * 返回队列中等待执行的任务数
     * @return
     */
    public int getQueueSize(){

        return executor.getQueue().size();
    }

    /**
     * 返回已执行完别的任务数
     * @return
     */
    public long getCompletedTaskCount(){

        return executor.getCompletedTaskCount();
    }

    /**
     * 结束线程池
     */
    public void shutdown(){
        if (executor != null){
            executor.shutdown();
        }
    }
}


