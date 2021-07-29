package com.example.demo;

import com.example.demo.rpcsimple.common.ContextConf;
import com.example.demo.rpcsimple.server.RpcServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(locations = {"classpath:spring-all.xml"})
public class DemoApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(DemoApplication.class, args);
        // 注册服务
        ContextConf.registServer();

        RpcServer rpcServer = new RpcServer("127.0.0.1", 8888, 100, 100);
        // 服务端启动
        rpcServer.start();
    }

}

