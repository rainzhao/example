package com.example.demo.rpcsimple.serverdemo;

import com.example.demo.rpcsimple.serverdemo.model.MyResponse;

/**
 * @author zhaoyu
 * @date 2019/11/15
 * @description: TODO
 */
public interface ServerInfoTest3 {

    MyResponse getMyResponse(String username, int age);

}
