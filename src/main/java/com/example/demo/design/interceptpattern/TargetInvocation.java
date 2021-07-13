package com.example.demo.design.interceptpattern;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author: zhaoyu
 * @date: 2021/7/9
 * @description:
 */
public class TargetInvocation {

    private List<Interceptor> interceptorList = new ArrayList<>();

    private Iterator<Interceptor> interceptors;

    private Target target;

    private Request request;

    public void setTarget(Target target) {
        this.target = target;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Response invoke() {
        if (interceptors.hasNext()) {
            Interceptor interceptor = interceptors.next();
            // 此处如果去掉return，按照书中来，target会被调用多次。interceptor.intercept(this);
            return interceptor.intercept(this);
        }
        return target.execute(request);
    }

    public void addInterceptor(Interceptor interceptor) {
        interceptorList.add(interceptor);
        interceptors = interceptorList.iterator();
    }
}
