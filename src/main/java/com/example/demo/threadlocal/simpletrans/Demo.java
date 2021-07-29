package com.example.demo.threadlocal.simpletrans;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author: zhaoyu
 * @date: 2021/7/16
 * @description:
 */
public class Demo {

    // 使用 ThreadLocal：
    //   - 可以 不同的线程（如执行不同的请求用户的处理） 有自己的Context。
    //   - 避免 Context 总是通过 函数参数 传递，中间路过的业务的逻辑 都关注/感知 框架的上下文
    private static final ThreadLocal<Map<String, String>> contextHolder = ThreadLocal.withInitial(HashMap::new);

    private static InheritableThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();

    /**
     * 在线程池的情况下，第一次创建线程的时候会从父线程中copy inheritableThreadLocals中的数据，所以第一个任务成功拿到了父线程设置的”i am a inherit
     * parent“，第二个任务执行的时候复用了第一个任务的线程，并不会触发复制父线程中的inheritableThreadLocals操作，所以即使在主线程中设置了新的值，也会不生效。同时get()
     * 方法是直接操作inheritableThreadLocals这个变量的，所以就直接拿到了第一个任务设置的值。
     * ————————————————
     * 原文链接：https://blog.csdn.net/vivo_tech/article/details/113500269
     * @throws Exception
     */
    private static void inheritableTrans() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        inheritableThreadLocal.set("i am a inherit parent");
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(inheritableThreadLocal.get());
            }
        });

        TimeUnit.SECONDS.sleep(1);
        inheritableThreadLocal.set("i am a new inherit parent");// 设置新的值

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(inheritableThreadLocal.get());
            }
        });
    }

    private static void threadTrans() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        // 1. *业务逻辑*需要做的实现逻辑：获取当前上下文
        final Map<String, String> context = getContext();

        // 2. *业务逻辑*需要做的实现逻辑：捕捉上下文
        Map<String, String> contextCopy = new HashMap<>(context);
        contextCopy.put("a", "a1");

        executorService.execute(() -> {
            // 在异步执行逻辑中，
            // 3. 传递，通过 Lambda的外部变量 contextCopy
            // 4. 使用上下文
            System.out.println(contextCopy);
        });

        contextCopy.put("a", "a2");

        //TimeUnit.SECONDS.sleep(1);

        executorService.execute(() -> {
            // 在异步执行逻辑中，
            // 3. 传递，通过 Lambda的外部变量 contextCopy
            // 4. 使用上下文
            System.out.println(contextCopy);
        });

    }

    public static void main(String[] args) throws Exception {
        threadTrans();

        inheritableTrans();
    }

    private static Map<String, String> getContext() {
        return contextHolder.get();
    }

}
