package com.example.demo.rpcsimple.util;

import com.example.demo.rpcsimple.common.ContextConf;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhaoyu
 * @date 2019-05-23
 */
public class ContextResolverUtils {

    private static AtomicInteger number = new AtomicInteger();


    /**
     * 获取目标对象实现的接口
     */
    public static Class<?>[] getImplementInterfaces(Object bean) {
        Class<?>[] beanInterfaces;
        if (AopUtils.isAopProxy(bean)) {
            Advised advised = (Advised) bean;
            beanInterfaces = advised.getTargetClass().getInterfaces();
        } else {
            beanInterfaces = bean.getClass().getInterfaces();
        }
        return beanInterfaces;
    }

    public static Class getServiceIface(Class<?>[] beanInterfaces) {
        Class serviceInterface = beanInterfaces[0];
        return serviceInterface;
    }


    public static Method getMethod(String methodName, String ifaceName, Object[] params, Class clazz) throws Exception {
        ClazzInfo clazzInfo = ContextConf.registClassMap.get(ifaceName);

        Map<String, List<Class>> methodParams = clazzInfo.getMethodParams();

        boolean isFind = false;
        if (methodParams != null && methodParams.entrySet().size() > 0) {
            for (Map.Entry<String, List<Class>> entry : methodParams.entrySet()) {
                List<Class> paramsList = entry.getValue();
                if (params.length == paramsList.size()) {
                    for (int i = 0; i < params.length; i++) {
                        isFind = getParameterTypes(params[i]).equalsIgnoreCase(paramsList.get(i).getName());
                        if (!isFind) {
                            break;
                        }
                    }
                }
                if (isFind) {
                    Class[] paramsClassList = new Class[params.length];
                    paramsList.toArray(paramsClassList);
                    return clazz.getDeclaredMethod(methodName, paramsClassList);
                }
            }
        } else {
            return clazz.getDeclaredMethod(methodName);
        }



        // 没有找到  undeclared method
        return null;
    }


    public static void initClazzInfo(String methodName, Class clazz) {
        Method[] declaredMethods = clazz.getDeclaredMethods();
        String interfaceName = clazz.getName();
        ClazzInfo clazzInfo = new ClazzInfo();
        for (Method declaredMethod : declaredMethods) {
            boolean b = declaredMethod.getName().equalsIgnoreCase(methodName);
            if (b) {
                int i = number.incrementAndGet();
                Parameter[] parameters = declaredMethod.getParameters();
                if (parameters.length > 0) {
                    List<Class> paramsList = new ArrayList<>();
                    Map<String, List<Class>> methodParams = new ConcurrentHashMap<>();
                    for (Parameter parameter : parameters) {

                        paramsList.add(parameter.getType());
                    }
                    methodParams.put(methodName + i, paramsList);
                    clazzInfo.setClazz(clazz);
                    clazzInfo.setMethodParams(methodParams);

                } else {
                    // 如果参数列表为空 直接返回该method
                    clazzInfo.setClazz(clazz);
                }
            }
        }
        ContextConf.registClassMap.put(interfaceName, clazzInfo);

    }

    private static String getParameterTypes(Object args) throws Exception {
        if (args == null) {
            return null;
        }
        if (args instanceof Integer) {
            return Integer.TYPE.getName();
        } else if (args instanceof Byte) {
            return Byte.TYPE.getName();
        } else if (args instanceof Short) {
            return Short.TYPE.getName();
        } else if (args instanceof Float) {
            return Float.TYPE.getName();
        } else if (args instanceof Double) {
            return Double.TYPE.getName();
        } else if (args instanceof Character) {
            return Character.TYPE.getName();
        } else if (args instanceof Long) {
            return Long.TYPE.getName();
        } else if (args instanceof Boolean) {
            return Boolean.TYPE.getName();
        } else {
            return args.getClass().getName();
        }
    }

}
