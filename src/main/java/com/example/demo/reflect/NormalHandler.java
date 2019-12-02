package com.example.demo.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author zhaoyu
 * @date 2019-07-22
 */
public class NormalHandler implements InvocationHandler {

    private Object target;

    public NormalHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // debug时编译器会执行该target的toString方法，而toString又是不需要代理的，故以此屏蔽掉
        if (method.getName().equalsIgnoreCase("toString")) {
            return null;
        }

        System.out.println("invoke method : " + method.getName());
        Object invoke = method.invoke(target, args);
        return invoke;
    }
}
