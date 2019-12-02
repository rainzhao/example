package com.example.demo.rpcsimple.serverdemo.impl;

import com.example.demo.rpcsimple.annotation.SoaService;
import com.example.demo.rpcsimple.serverdemo.ServerInfoTest;

/**
 * @author zhaoyu
 * @date 2019-05-24
 */
@SoaService
public class ServerInfoTestImpl implements ServerInfoTest {
    @Override
    public String test() {
        return "server receive test message";
    }
}
