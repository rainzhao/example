package com.example.demo.taskrun;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author zhaoyu
 * @date 2019-08-17
 */
public class ThreadPoolManager {


    private static final ExecutorService executor = new ThreadPoolExecutor
            (5, Runtime.getRuntime().availableProcessors() * 2,
                    60L, TimeUnit.SECONDS,
                    new ArrayBlockingQueue<>(1024));


    public static <T extends Callable> ThreadPoolCompletionService submitAll(T... tasks) {
        if (tasks != null && tasks.length > 0) {
            List<Callable> callableList = new ArrayList<>();
            for (Callable task : tasks) {
                if (task != null) {
                    callableList.add(task);
                }
            }
            return realSubmit(callableList);
        }
        return null;
    }

    private static ThreadPoolCompletionService realSubmit(List<Callable> callableList) {
        if (!CollectionUtils.isEmpty(callableList)) {
            ThreadPoolCompletionService completionService = new ThreadPoolCompletionService(executor);
            for (Callable callable : callableList) {
                completionService.submit(callable);
            }
            return completionService;
        }
        return null;
    }


}
