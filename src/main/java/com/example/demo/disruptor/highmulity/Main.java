package com.example.demo.disruptor.highmulity;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

/**
 * @author zhaoyu
 * @date 2019-02-28
 */
public class Main {

    public static void main(String[] args) throws Exception{

        // 1.创建ringBuffer
        RingBuffer<Order> ringBuffer = RingBuffer.create(
                ProducerType.MULTI,
                new EventFactory<Order>() {
                    @Override
                    public Order newInstance() {
                        return new Order();
                    }
                },
                1024 * 1024,
                new YieldingWaitStrategy()
        );

        // 2.通过ringBuffer创建一个屏障
        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();

        // 3. 创建多消费者数组：
        Consumer[] consumers = new Consumer[10];

        for (int i = 0; i < consumers.length; i++) {
            consumers[i] = new Consumer("C" + i);
        }

        // 4. 构建多消费者工作池
        WorkerPool<Order> workerPool = new WorkerPool<>(
                ringBuffer,
                sequenceBarrier,
                new EventExceptionHandler(),
                consumers
        );

        // 5. 设置多个消费者的序号，用于单独统计消费，并且设置到ringBuffer中
        ringBuffer.addGatingSequences(workerPool.getWorkerSequences());

        // 6. 启动workPool
        workerPool.start(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));

        final CountDownLatch latch = new CountDownLatch(1);
        for (int i = 0; i < 100; i++) {
            Producer producer = new Producer(ringBuffer);
            new Thread(() -> {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int j = 0; j < 100; j++) {
                    producer.sendData(UUID.randomUUID().toString());
                }
            }).start();
        }

        Thread.sleep(2000);
        System.out.println("线程创建完毕，开始生产数据=======================");
        latch.countDown();

        System.out.println("消费者处理的总数-----------" + consumers[2].getCount());
    }


    static class EventExceptionHandler implements ExceptionHandler<Order> {

        @Override
        public void handleEventException(Throwable throwable, long l, Order order) {
            //消费时
        }

        @Override
        public void handleOnStartException(Throwable throwable) {
            // 刚启动
        }

        @Override
        public void handleOnShutdownException(Throwable throwable) {
            // 结束
        }
    }

}
