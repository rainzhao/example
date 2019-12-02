package com.example.demo.concurrent;

/**
 * @author zhaoyu
 * @date 2019-07-25
 */
public class DeadLock {

    private static Object lockA = new Object();

    private static Object lockB = new Object();


    private volatile static boolean flag = false;


    public static void main(String[] args) {

        new Thread(() -> {
            while (true) {
                synchronized (lockA) {
                    if (flag) {
                        flag = false;
                        synchronized (lockB) {
                            System.out.println("thread a get all lock");
                        }
                    }

                }
            }
        }).start();


        new Thread(() -> {
            while (true) {
                synchronized (lockB) {
                    flag = true;
                    synchronized (lockA) {
                        System.out.println("thread b get all Lock");
                    }
                }
            }
        }).start();


        System.out.println("main has run end");


    }


}
