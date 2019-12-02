package com.example.demo.interrupt;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author zhaoyu
 * @date 2019-06-20
 */
public class InterruptWaitingDemo extends Thread {

    private static CountDownLatch runLatch = new CountDownLatch(1);

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // 重设中断标志位,因为会被清空
                System.out.println("设置中断标志位, 当前为：" + Thread.currentThread().isInterrupted());
                Thread.currentThread().interrupt();
                System.out.println(Thread.currentThread().getName());
                System.out.println("重新设置中断标志位后：" + Thread.currentThread().isInterrupted());
                runLatch.countDown();
            }
        }
        System.out.println("重新设置中断标志位后：" + Thread.currentThread().isInterrupted());
    }

    public static void main(String[] args) throws Exception {
        Thread t = new InterruptWaitingDemo();
        t.start();
        TimeUnit.SECONDS.sleep(1);
        t.interrupt();
        runLatch.await();
        System.out.println(t.getName());

        while (!t.isInterrupted()) {
            System.out.println("查看中断标志位" + t.isInterrupted());
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println("查看中断标志位" + t.isInterrupted());

    }
}
