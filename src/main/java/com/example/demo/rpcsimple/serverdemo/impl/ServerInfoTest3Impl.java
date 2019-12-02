package com.example.demo.rpcsimple.serverdemo.impl;

import com.example.demo.rpcsimple.annotation.SoaService;
import com.example.demo.rpcsimple.serverdemo.ServerInfoTest3;
import com.example.demo.rpcsimple.serverdemo.model.MyResponse;

/**
 * @author zhaoyu
 * @date 2019/11/15
 * @description: TODO
 */
@SoaService
public class ServerInfoTest3Impl implements ServerInfoTest3 {

    @Override
    public MyResponse getMyResponse(String username, int age) {
        MyResponse myResponse = new MyResponse();
        username = username + " server response zhaoyu";
        myResponse.setUsername(username);
        myResponse.setAge(age);
        return myResponse;
    }
}
