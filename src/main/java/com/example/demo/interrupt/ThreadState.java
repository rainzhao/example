package com.example.demo.interrupt;

import java.util.concurrent.CountDownLatch;

/**
 * @author zhaoyu
 * @date 2019-06-28
 */
public class ThreadState {


    public static void main(String[] args) throws Exception{
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Thread thread = new Thread(() -> {

        });
        thread.setName("whileTrue thread state");

        thread.start();

        countDownLatch.await();

    }
}
