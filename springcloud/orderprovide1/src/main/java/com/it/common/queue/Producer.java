package com.it.common.queue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * @author 18576756475
 * @version V1.0
 * @ClassName Producer
 * @Description TODO
 * @Date 2019-12-18 10:43:32
 **/
public class Producer implements Runnable {

    private final BlockingQueue<Integer> blockingQueue;
    private Random random;

    public Producer(BlockingQueue<Integer> blockingQueue) {
        this.blockingQueue = blockingQueue;
        random = new Random();

    }

    public void run() {
        int info = random.nextInt(100);
        try {
            blockingQueue.put(info);
            Thread.sleep(50);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
    }
}
