package com.example.demo.design.interceptpattern;

/**
 * @author: zhaoyu
 * @date: 2021/7/9
 * @description:
 */
public interface Interceptor {

    Response intercept(TargetInvocation targetInvocation);

}
