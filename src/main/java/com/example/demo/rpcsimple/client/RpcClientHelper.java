package com.example.demo.rpcsimple.client;

import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * @author zhaoyu
 * @date 2019-06-01
 */
public class RpcClientHelper {



    public static <T> T getClient(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(RpcClientHelper.class.getClassLoader(),
                new Class<?>[]{clazz},  new ClientInvoker<T>());
    }



}
