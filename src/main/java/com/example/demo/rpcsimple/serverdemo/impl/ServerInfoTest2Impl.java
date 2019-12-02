package com.example.demo.rpcsimple.serverdemo.impl;

import com.example.demo.rpcsimple.annotation.SoaService;
import com.example.demo.rpcsimple.serverdemo.ServerInfoTest2;

/**
 * @author zhaoyu
 * @date 2019/11/14
 * @description: TODO
 */
@SoaService
public class ServerInfoTest2Impl implements ServerInfoTest2 {
    @Override
    public String getData(String id, int number) {
        return "id is " + id + " , number + " + number;
    }
}
