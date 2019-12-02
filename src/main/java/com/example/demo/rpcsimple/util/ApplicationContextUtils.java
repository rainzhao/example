package com.example.demo.rpcsimple.util;

import org.springframework.context.ApplicationContext;

/**
 * @author zhaoyu
 * @date 2019-05-23
 */
public class ApplicationContextUtils {

    public static ApplicationContext applicationContext;

    public static Object get(String name) {
        return applicationContext.getBean(name);
    }

    public static Object get(Class<?> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static boolean has(String name) {
        return applicationContext.containsBean(name);
    }
}
