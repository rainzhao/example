package com.example.demo.disruptor.quickstart;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * @author zhaoyu
 * @date 2019-02-22
 */
public class OrderEventProducer {

    private RingBuffer<OrderEvent> ringBuffer;
    public OrderEventProducer(RingBuffer<OrderEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void sendData(ByteBuffer data) {

        // 1. 生产者发送消息的时候首先需要从ringbuffer里面获取一个可用的序号
        long sequence = ringBuffer.next();
        try {
            // 2. 根据这个序号找到具体的" OrderEvent  " 元素 注意：此时获取的OrderEvent是一个空的对象， 没有被赋值的空对象
            OrderEvent orderEvent = ringBuffer.get(sequence);
            // 3. 进行实际的赋值处理
            orderEvent.setValue(data.getLong(0));
        } finally {
            // 4. 提交发布操作
            ringBuffer.publish(sequence);
        }
    }
}
