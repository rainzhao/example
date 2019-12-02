package com.example.demo.interrupt;

/**
 * @author zhaoyu
 * @date 2019-06-20
 */
public class ThrowInterupt {


    public static void interruptMethod() throws InterruptedException {
        Thread.sleep(1000);
    }


    public static void main(String[] args) {
        Thread mainThread = Thread.currentThread();
        System.out.println(mainThread.getName());
        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("1234");
                e.printStackTrace();
            }
            mainThread.interrupt();
        }).start();

        try {
            interruptMethod();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }


}
