package com.example.demo.waitnotify;

/**
 * @author zhaoyu
 * @date 2019-05-31
 */
public class WaitThread extends Thread {
    private volatile boolean fire = false;

    @Override
    public void run() {
        try {
            synchronized(this) {
                while(!fire) {
                    wait();
                }
            }
            System.out.println("fired ");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public synchronized void fire() {
        this.fire = true;
        notify();
    }


    public static void main(String[] args) throws InterruptedException {
        WaitThread waitThread = new WaitThread();
        waitThread.start();
        Thread.sleep(1000);
        System.out.println("main fire");
        waitThread.fire();
    }

}
