package com.example.demo.qleexpress;

import com.ql.util.express.Operator;

/**
 * @author: zhaoyu
 * @date: 2021/6/21
 * @description:
 */
public class JoinOperator extends Operator {

    @Override
    public Object executeInner(Object[] list) throws Exception {
        Object o1 = list[0];
        Object o2 = list[1];
        java.util.List result = new java.util.ArrayList();

        if (o1 instanceof java.util.List) {
            result.addAll((java.util.List)o1);
        } else {
            result.add(o1);
        }
        for (int i = 1; i < list.length; i++) {
            result.add(list[i]);
        }
        return result;
    }
}
