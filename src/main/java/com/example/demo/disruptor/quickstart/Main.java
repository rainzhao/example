package com.example.demo.disruptor.quickstart;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhaoyu
 * @date 2019-02-22
 */
public class Main {
    public static void main(String[] args) {


        /**
         * 1. eventFactory消息（event）工厂对象
         * 2. ringBufferSize: 消息长度
         * 3. executor: 线程池 拒绝策略
         * 4. ProduceType 单生产者还是多生产者
         * 5. WaitStrategy 等待策略
         */

        // 参数准备工作
        OrderEventFactory orderEventFactory = new OrderEventFactory();
        int ringBufferSize = 1024 * 1024;
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        // 1. 实例化disruptor对象
        Disruptor<OrderEvent> disruptor = new Disruptor<OrderEvent>(
                orderEventFactory,
                ringBufferSize,
                executorService,
                ProducerType.SINGLE,
                new BlockingWaitStrategy());

        // 2. 添加消费者的监听 (disruptor 与消费者的关联关系)
        disruptor.handleEventsWith(new OrderEventHandler());

        // 3. 启动disruptor
        disruptor.start();

        // 4. 获取实际存储数据的容器 RingBuffer
        RingBuffer<OrderEvent> ringBuffer = disruptor.getRingBuffer();

        OrderEventProducer producer = new OrderEventProducer(ringBuffer);

        ByteBuffer bb = ByteBuffer.allocate(8);

        for (long i = 0; i < 100; i++) {
            bb.putLong(0, i);
            producer.sendData(bb);
        }

        disruptor.shutdown();
        executorService.shutdown();

    }
}
