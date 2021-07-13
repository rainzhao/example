package com.example.demo.design.interceptpattern.impl;

import com.example.demo.design.interceptpattern.Interceptor;
import com.example.demo.design.interceptpattern.Response;
import com.example.demo.design.interceptpattern.TargetInvocation;

/**
 * @author: zhaoyu
 * @date: 2021/7/9
 * @description:
 */
public class AuditInterceptor implements Interceptor {
    @Override
    public Response intercept(TargetInvocation targetInvocation) {

        if (targetInvocation == null) {
            throw new IllegalArgumentException("target is null");
        }

        System.out.println("Audit Succeed");
        return targetInvocation.invoke();

    }
}
