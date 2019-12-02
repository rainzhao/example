package com.example.demo.disruptor.highmulity;

import com.lmax.disruptor.RingBuffer;

/**
 * @author zhaoyu
 * @date 2019-02-28
 */
public class Producer {
    private RingBuffer<Order> ringBuffer;

    public Producer(RingBuffer<Order> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void sendData(String data){
        long sequence = ringBuffer.next();
        try {
            Order order = ringBuffer.get(sequence);
            order.setId(data);
        } finally {
            ringBuffer.publish(sequence);
        }

    }
}
