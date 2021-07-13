package com.example.demo.disruptor.high;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhaoyu
 * @date 2019-02-26
 */
public class Main {
    public static void main(String[] args) {
        //构建一个线程池用于提交任务
        ExecutorService es = Executors.newFixedThreadPool(4);
        ExecutorService es2 = Executors.newFixedThreadPool(4);
        // 构建disruptor
        Disruptor<Trade> disruptor = new Disruptor<Trade>(
                new EventFactory<Trade>() {
                    @Override
                    public Trade newInstance() {
                        return new Trade();
                    }
                },
                1024 * 1024,
                es2,
                ProducerType.SINGLE,
                new BusySpinWaitStrategy()
        );

        // 2 handleEventsWith
        disruptor.handleEventsWith(new Handler());

        RingBuffer<Trade> ringBuffer = disruptor.getRingBuffer();

        CountDownLatch countDownLatch = new CountDownLatch(1);

        es.submit(new TradePublisher(countDownLatch, disruptor));

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        disruptor.shutdown();
        es2.shutdown();
        es.shutdown();

    }
}
