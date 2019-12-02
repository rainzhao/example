package com.example.demo.threadpool;

/**
 * @author zhaoyu
 * @date 2019-06-27
 */
public interface Callable<T> {

    /**
     * 执行任务
     * @return 执行结果
     */
    T call() ;
}
