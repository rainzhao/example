package com.example.demo.rpcsimple.common;

import com.example.demo.rpcsimple.annotation.SoaService;
import com.example.demo.rpcsimple.serverdemo.ServerInfoTest;
import com.example.demo.rpcsimple.util.ApplicationContextUtils;
import com.example.demo.rpcsimple.util.ClazzInfo;
import com.example.demo.rpcsimple.util.ContextResolverUtils;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhaoyu
 * @date 2019-05-25
 */
public class ContextConf {

    public static ConcurrentHashMap<String, Class> registServerMap = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, ClazzInfo> registClassMap = new ConcurrentHashMap<>();

    public static void registServer() throws Exception{
        ApplicationContext applicationContext = ApplicationContextUtils.applicationContext;

        // 获取注解是SoaService的bean
        Map<String, Object> soaBeans = applicationContext.getBeansWithAnnotation(SoaService.class);
        for (Map.Entry<String, Object> entry : soaBeans.entrySet()) {

            Class<?>[] beanInterfaces = ContextResolverUtils.getImplementInterfaces(entry.getValue());

            Class serviceIface = ContextResolverUtils.getServiceIface(beanInterfaces);

            Class<?> clazz = Class.forName(serviceIface.getName());

            Method[] methods = clazz.getMethods();

            for (Method method : methods) {
                String methodName = method.getName();
                ContextResolverUtils.initClazzInfo(methodName, serviceIface);
            }

            registServerMap.put(serviceIface.getName(), serviceIface);
        }
    }

    public static ConcurrentHashMap<String, Class> getRegistServerMap() {
        return registServerMap;
    }

    public static void main(String[] args) {


        Class serviceIface = ContextResolverUtils.getServiceIface(new Class[]{ServerInfoTest.class});

        System.out.println(serviceIface);
    }

}
