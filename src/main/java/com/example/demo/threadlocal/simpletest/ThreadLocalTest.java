package com.example.demo.threadlocal.simpletest;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: zhaoyu
 * @date: 2021/7/16
 * @description:
 */
public class ThreadLocalTest {

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 5, 5, TimeUnit.MINUTES,
            new ArrayBlockingQueue<Runnable>(1024));

    private static void testThreadPoolLocal() throws Exception {
        ThreadLocal threadLocal = new ThreadLocal();
        threadLocal.set("dd");

        executor.execute(() -> {
            System.out.println("testThreadPoolLocal:" + threadLocal.get());
        });

    }

    private static void testNewThreadLocal() throws Exception {
        ThreadLocal threadLocal = new ThreadLocal();
        threadLocal.set("ddcc");

        Thread thread = new Thread(){
            @Override
            public void run() {
                System.out.println("testNewThreadLocal:" + threadLocal.get());
            }
        };

        thread.start();
    }

    private static void testInheritableThreadLocal() throws Exception {
        ThreadLocal threadLocal = new InheritableThreadLocal();
        threadLocal.set("myValue");

        executor.execute(() -> {
            System.out.println("testInheritableThreadLocal:" + threadLocal.get());
        });
        executor.shutdown();
        while (!executor.awaitTermination(100, TimeUnit.MILLISECONDS)) {
            System.out.println("thread running");
        }
    }


    public static void main(String[] args) throws Exception {
        testNewThreadLocal();

        testThreadPoolLocal();

        testInheritableThreadLocal();


        executor.shutdown();
        while (!executor.awaitTermination(100, TimeUnit.MILLISECONDS)) {
            System.out.println("thread running");
        }
        Thread.sleep(1000);
    }

}
