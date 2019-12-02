package com.example.demo.disruptor.highmulity;

import com.lmax.disruptor.WorkHandler;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhaoyu
 * @date 2019-02-28
 */
public class Consumer implements WorkHandler<Order> {

    private String consumerId;

    private static AtomicInteger count = new AtomicInteger(0);

    private Random random = new Random();

    public Consumer(String consumerId) {
        this.consumerId = consumerId;
    }

    @Override
    public void onEvent(Order o) throws Exception {
        Thread.sleep(1 * random.nextInt(5));
        System.out.println("当前消费者" + consumerId + "信息" + o.getId());
        count.incrementAndGet();
    }

    public int getCount() {
        return count.get();
    }


}
