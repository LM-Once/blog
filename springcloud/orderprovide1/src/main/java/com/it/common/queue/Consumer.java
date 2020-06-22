package com.it.common.queue;

import java.util.concurrent.BlockingQueue;

/**
 * @author 18576756475
 * @version V1.0
 * @ClassName Consumer
 * @Description TODO
 * @Date 2019-12-18 10:45:00
 **/
public class Consumer implements Runnable {

    private final BlockingQueue<Integer> blockingQueue;
    public Consumer(BlockingQueue<Integer> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }
    public void run() {
        while(true){
            int info;
            try {
                info = blockingQueue.take();
                Thread.sleep(50);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
