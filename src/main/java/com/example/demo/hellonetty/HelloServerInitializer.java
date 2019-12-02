package com.example.demo.hellonetty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author zhaoyu
 * @date 2019-05-17
 * 初始化器。当channel注册后，会执行里面的相应的初始化方法
 */
public class HelloServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        // 通过channel获取对应的管道
        ChannelPipeline pipeline = channel.pipeline();
        // 通过管道添加handler，是由netty提供的助手类
        // 当请求到服务端，我们需要做编解码，响应到客户端做解码
        pipeline.addLast("HttpServerCodec", new HttpServerCodec());
        // 添加自定义的助手类，返回'hello netty'
        pipeline.addLast("customHandler", new CustomHadler());

    }
}
