package com.it.common.queue;

import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author 18576756475
 * @version V1.0
 * @ClassName Executor
 * @Description 执行器
 * @Date 2019-12-16 15:57:00
 **/
public class Excutor {

    private static Excutor excutor;

    private static LinkedBlockingQueue queue = new LinkedBlockingQueue();

    private Excutor(){

    }

    public static Excutor getInstance(){
        if (excutor == null){
            excutor = new Excutor();
        }
        return excutor;
    }

    public void put(){
        try {
            queue.put("ok");
            System.out.println("hello");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void take(){
        try {
            queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("00000");
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    Excutor.getInstance().put();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Excutor.getInstance().put();
        System.out.println("111111111111");
        Excutor.getInstance().take();
        System.out.println("2232323");
    }
}
