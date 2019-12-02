package com.example.demo.hellonetty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author zhaoyu
 * @date 2019-05-17
 * @description 实现客户端发送一个请求，服务器会返回hello netty
 */
public class HelloServer {
    public static void main(String[] args) throws Exception {
        // 定义一个线程组
        // 主线程组 , 用于接受客户端的连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 从线程组 , 老板线程组会把任务丢给它，让手下线程组去做任务
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // netty 服务器的创建，ServerBootStrap是一个启动类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup) // 设置主从线程组
                    .channel(NioServerSocketChannel.class) // 设置nio的双向通道
                    .childHandler(new HelloServerInitializer());  // 子处理器，用于处理workergroup

            // 启动server 并设置端口号，并同时设置启动方式为同步
            ChannelFuture channelFuture = serverBootstrap.bind(8088)
                    .sync();

            // 监听关闭的channel。设置同步的方式
            channelFuture.channel().closeFuture().sync();
        } finally {
            // 关闭线程池
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }


}
