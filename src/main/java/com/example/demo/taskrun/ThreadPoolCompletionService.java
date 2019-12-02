package com.example.demo.taskrun;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * @author zhaoyu
 * @date 2019-08-17
 */
public class ThreadPoolCompletionService<V> {

    private static final long TIME_BUDGET = TimeUnit.SECONDS.toNanos(40);
    /**
     * 执行任务的service队列
     */
    private ExecutorCompletionService<V> executorCompletionService;
    /**
     * Error key
     */
    private static final String ERROR_KEY = "ThreadPoolCompletionService";
    /**
     * 任务列表
     */
    private List<Future<V>> threadFutureTasks;

    private long endNanos;

    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolCompletionService.class);


    /**
     * 构造completionService
     * @param executor
     */
    public ThreadPoolCompletionService(ExecutorService executor) {
        this.executorCompletionService = new ExecutorCompletionService<>(executor);
    }

    public synchronized void submit(Callable<V> callable) {
        if (threadFutureTasks == null) {
            threadFutureTasks = new ArrayList<>();
        }
        endNanos = System.nanoTime() + TIME_BUDGET;
        Future<V> submit = executorCompletionService.submit(callable);
        threadFutureTasks.add(submit);

    }

    public void getResult() {
        convertResult();
        //convertResult2();
    }

    public void convertResult2() {
        try {
            Future<V> poll = executorCompletionService.poll(2, TimeUnit.SECONDS);
            if (!poll.isDone()) {
                poll.cancel(true);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void convertResult() {
        long timeLeft = endNanos - System.nanoTime();
        boolean done = false;
        try {
            for (Future<V> future : threadFutureTasks) {
                try {
                    if (timeLeft > 0 && !future.isDone()) {
                        future.get(timeLeft, NANOSECONDS);
                    }
                } catch (TimeoutException ex) {
                    logger.error("thread has timeout", ex);
                } catch (Exception e) {
                    logger.error(ERROR_KEY, e);
                }
                done = true;
            }
        } finally {
            if (!done) {
                for (int i = 0, size = threadFutureTasks.size(); i < size; i++)
                    threadFutureTasks.get(i).cancel(true);
            }
        }
    }



}
