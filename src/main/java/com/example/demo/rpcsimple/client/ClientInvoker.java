package com.example.demo.rpcsimple.client;

import com.alibaba.fastjson.JSON;
import com.example.demo.rpcsimple.model.RequestInfo;
import com.example.demo.rpcsimple.util.ApplicationContextUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author zhaoyu
 * @date 2019-06-01
 */
public class ClientInvoker<T> implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (method.getName().equalsIgnoreCase("toString")) {
            return this.toString();
        }

        RpcClient rpcClient = (RpcClient) ApplicationContextUtils.get("rpcClientInfo");
        System.out.println("proxy execute method" + method.getName() + " 方法");
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setInterfaceName(method.getDeclaringClass().getName());
        requestInfo.setMethodName(method.getName());
        List<String> params = new ArrayList<>();
        if (args != null && args.length > 0) {
            for (Object arg : args) {
                String typeName = arg.getClass().getName();
                params.add(typeName);
            }
        }
        requestInfo.setParams(params);
        requestInfo.setArgs(args);
        //RpcFuture<Object> future = rpcClient.sendAsync("type", requestInfo);

        rpcClient.sendTest("type", requestInfo);

//        try {
//            String responseStr = (String) future.get();
//            String responseStr = "";
//            RequestInfo response = JSON.parseObject(responseStr, RequestInfo.class);
//
//            Object o1 = JSON.parseObject(response.getData(), method.getReturnType());
//
//            return o1;
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }

        return null;
    }
}
