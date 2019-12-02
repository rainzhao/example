package com.example.demo.rpcsimple.util;

import java.util.List;
import java.util.Map;

/**
 * @author zhaoyu
 * @date 2019/11/14
 * @description: TODO
 */
public class ClazzInfo {

    /**
     * 类
     */
    private Class clazz;

    /**
     *  k -> methodName 重载方法
     *  v -> paramList
     */
    private Map<String, List<Class>> methodParams;

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public Map<String, List<Class>> getMethodParams() {
        return methodParams;
    }

    public void setMethodParams(Map<String, List<Class>> methodParams) {
        this.methodParams = methodParams;
    }
}
