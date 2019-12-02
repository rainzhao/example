package com.example.demo.disruptor.quickstart;


import com.lmax.disruptor.EventHandler;

/**
 * @author zhaoyu
 * @date 2019-02-22
 */
public class OrderEventHandler implements EventHandler<OrderEvent> {
    @Override
    public void onEvent(OrderEvent orderEvent, long l, boolean b) throws Exception {
        System.out.println("消费者：" + orderEvent.getValue());
    }
}
