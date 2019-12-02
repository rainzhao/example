package com.example.demo.rpcsimple.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhaoyu
 * @date 2019-05-25
 */
public class RequestInfo implements Serializable {


    private static final long serialVersionUID = 4539827435008635550L;
    private String data;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 接口名称
     */
    private String interfaceName;

    /**
     * 请求参数类型
     */
    private List<String> params;

    private Object[] args;


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
