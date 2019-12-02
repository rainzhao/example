package com.example.demo.taskrun;

import java.util.concurrent.Callable;

/**
 * @author zhaoyu
 * @date 2019-08-17
 */
public class ServiceTask<T> implements Callable<T> {

    /**
     * callable
     */
    private Callable<T> callable;

    private  T result;

    public ServiceTask(Callable<T> callable) {
        this.callable = callable;
    }

    @Override
    public T call() throws Exception {
        T result = callable.call();
        this.result = result;
        return result;
    }

    public T getResult() {
        return result;
    }
}
