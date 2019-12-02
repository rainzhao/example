package com.example.demo.threadpool;

/**
 * @author zhaoyu
 * @date 2019-06-27
 */
public interface Future<T> {

    /**
     * 获取
     * @return 结果
     * @throws InterruptedException
     */
    T get() throws InterruptedException;
}
