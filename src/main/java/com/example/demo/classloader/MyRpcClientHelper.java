package com.example.demo.classloader;

//import com.alibaba.fastjson.JSONObject;
//import com.hellobike.soa.rpc.ClientInvoker;
//import com.hellobike.soa.rpc.RpcClientHelper;
//import com.hellobike.soa.rpc.auth.InternalInvoker;
//import com.hellobike.soa.rpc.interceptor.*;
//import org.apache.commons.lang.StringUtils;
//
//import java.lang.reflect.Method;
//import java.lang.reflect.Proxy;

/**
 * @author pengzhiheng
 * @createDate 2019-07-27
 * @description 封装RPC接口调用方式，一种是动态字节码加载的方式，一种是原有rpc调用方式
 */
//public class MyRpcClientHelper {
//
//    public static Object execution(String iface,String method,Object params) throws Exception{
//
//        Class clazz = DynamicGenerateClass.ifaceClasss.get(iface);
//        String clientName = DynamicGenerateClass.ifaceClientNames.get(iface);
//        Object obj ;
//        if(clazz == null || StringUtils.isBlank(clientName)) {
//            obj =  RpcClientHelper.getClient(Class.forName(iface));
//        } else {
//            ClientInvoker clientInvoker = new InternalInvoker(clientName);
//            obj = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, ClientInterceptors.intercept(clientName, clientInvoker, new ClientInterceptor[]{new RateLimitExceptionInterceptor(), new RateLimitInterceptor(clientName), new ServiceDowngradeInterceptor(clientName), new CircuitBreakerInterceptor(clientName)}));
//        }
//        Method executeMethod = obj.getClass().getMethod(method, JSONObject.class);
//        return executeMethod.invoke(obj, params);
//    }
//
//}
