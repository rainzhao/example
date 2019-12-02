package com.example.demo.rpcsimple.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoyu
 * @date 2019-06-03
 */
public class TestRequestInfo {



    public static void main(String[] args) {
        List<RequestInfo> requestInfoList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            RequestInfo requestInfo = new RequestInfo();
            requestInfo.setMethodName(String.valueOf(i) + "---");
            requestInfoList.add(requestInfo);
        }

    }
}
