package com.example.demo.disruptor.high;

import com.lmax.disruptor.dsl.Disruptor;

import java.util.concurrent.CountDownLatch;

/**
 * @author zhaoyu
 * @date 2019-02-26
 */
public class TradePublisher implements Runnable {

    private Disruptor<Trade> disruptor;
    private CountDownLatch countDownLatch;

    public TradePublisher(CountDownLatch countDownLatch, Disruptor<Trade> disruptor) {
        this.disruptor = disruptor;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {

    }
}
