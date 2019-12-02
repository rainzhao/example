package com.example.demo.rpcsimple.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhaoyu
 * @date 2019-06-01
 */
@Configuration
public class ClientRegist {

    @Bean(name = "rpcClientInfo")
    public RpcClient rpcClient() {
        RpcClient rpcClient = new RpcClient("127.0.0.1", 8888);
        // 客户端启动
        rpcClient.init();
        return rpcClient;
    }

}
