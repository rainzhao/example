package com.example.demo.threadpool;

import java.util.concurrent.TimeUnit;

/**
 * @author zhaoyu
 * @date 2019-06-28
 */
public class ShutDown {

    public static void main(String[] args) throws Exception{
        Runner one = new Runner();
        Thread countThread = new Thread(one , "CountThread");
        countThread.start();
        // 睡一秒，main线程对CountThread进行中断，使countThread能够感知中断而结束
        TimeUnit.SECONDS.sleep(1);
        countThread.interrupt();
        Runner two = new Runner();
        countThread = new Thread(two, "CountThread");
        countThread.start();
        // 睡一秒，main线程对Runner two进行取消，使countThread能够感知on 为false而结束
        TimeUnit.SECONDS.sleep(1);
        two.cancel();
    }

    private static class Runner implements Runnable {
        private long i;
        private volatile  boolean on = true;

        @Override
        public void run() {
            while(on && !Thread.currentThread().isInterrupted()) {
                i++;
            }
            System.out.println("Count i = " + i);
        }
        public void cancel() {
            on = false;
        }
    }

}
