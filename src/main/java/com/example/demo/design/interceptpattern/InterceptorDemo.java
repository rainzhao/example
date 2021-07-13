package com.example.demo.design.interceptpattern;

import com.alibaba.fastjson.JSON;
import com.example.demo.design.interceptpattern.impl.AuditInterceptor;
import com.example.demo.design.interceptpattern.impl.LogInterceptor;

/**
 * @author: zhaoyu
 * @date: 2021/7/9
 * @description:
 */
public class InterceptorDemo {


    public static void main(String[] args) {
        TargetInvocation targetInvocation = new TargetInvocation();
        targetInvocation.addInterceptor(new LogInterceptor());
        targetInvocation.addInterceptor(new AuditInterceptor());
        Request request1 = new Request();
        request1.setName("rainzhao");
        targetInvocation.setRequest(request1);
        targetInvocation.setTarget(request -> {
            System.out.println("执行目标方法");
            Response response = new Response();
            response.setData("1234");
            return response;
        });
        // 执行
        Response invoke = targetInvocation.invoke();
        System.out.println(JSON.toJSONString(invoke));

    }

}
