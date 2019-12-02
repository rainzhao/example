package com.example.demo.rpcsimple.serverdemo.impl;

import com.example.demo.rpcsimple.annotation.SoaService;
import com.example.demo.rpcsimple.serverdemo.ServerInfoTest4;
import com.example.demo.rpcsimple.serverdemo.model.DataRequest;
import com.example.demo.rpcsimple.serverdemo.model.DataResponse;

/**
 * @author zhaoyu
 * @date 2019/11/15
 * @description: TODO
 */
@SoaService
public class ServerInfoTest4Impl implements ServerInfoTest4 {

    @Override
    public DataResponse test(DataRequest dataRequest) {

        DataResponse dataResponse = new DataResponse();
        dataResponse.setCode(dataRequest.getCode());
        dataResponse.setData(dataRequest.getData() + "server return ");
        return dataResponse;
    }
}
