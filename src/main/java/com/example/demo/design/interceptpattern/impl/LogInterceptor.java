package com.example.demo.design.interceptpattern.impl;

import com.example.demo.design.interceptpattern.Interceptor;
import com.example.demo.design.interceptpattern.Response;
import com.example.demo.design.interceptpattern.TargetInvocation;

/**
 * @author: zhaoyu
 * @date: 2021/7/9
 * @description:
 */
public class LogInterceptor implements Interceptor {

    @Override
    public Response intercept(TargetInvocation targetInvocation) {
        System.out.println("LogBegin... ");
        Response response = targetInvocation.invoke();
        System.out.println("LogEnd ...");
        return response;
    }
}
