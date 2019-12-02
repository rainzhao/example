package com.example.demo.rpcsimple.server;

import com.example.demo.rpcsimple.common.MessageDecoder;
import com.example.demo.rpcsimple.common.MessageEncoder;
import com.example.demo.rpcsimple.common.Spliter;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author zhaoyu
 * @date 2019-05-23
 */
public class RpcServer {

    private final static Logger LOG = LoggerFactory.getLogger(RpcServer.class);

    private String ip;
    private int port;
    private int ioThreads;
    private int workerThreads;

    public RpcServer(String ip, int port, int ioThreads, int workerThreads) {
        this.ip = ip;
        this.port = port;
        this.ioThreads = ioThreads;
        this.workerThreads = workerThreads;
    }

    private ServerBootstrap bootstrap;
    private EventLoopGroup boosGroup;
    private EventLoopGroup workerGroup;
    private MessageCollector collector;



    public void start() {
        bootstrap = new ServerBootstrap();
        boosGroup = new NioEventLoopGroup(ioThreads);
        workerGroup = new NioEventLoopGroup(ioThreads);



        collector = new MessageCollector(workerThreads);
        bootstrap.group(boosGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipe = ch.pipeline();
                // 通过管道添加handler，是由netty提供的助手类
                // 当请求到服务端，我们需要做编解码，响应到客户端做解码
                //pipe.addLast(new FixedLengthFrameDecoder(44));
                //pipe.addLast("HttpServerCodec", new HttpServerCodec());

                pipe.addLast(new Spliter());
                pipe.addLast(new MessageDecoder());
                pipe.addLast(new MessageEncoder());
                pipe.addLast(collector);

//                ch.pipeline().addLast(new Spliter());
//                ch.pipeline().addLast(new PacketDecoder());
//                ch.pipeline().addLast(collector);
//                ch.pipeline().addLast(new PacketEncoder());

            }
        });
        bootstrap.option(ChannelOption.SO_BACKLOG, 100).option(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.TCP_NODELAY, true).childOption(ChannelOption.SO_KEEPALIVE, true);
        //serverChannel = bootstrap.bind(this.ip, this.port).channel();

        bind(bootstrap, this.ip ,port);
        LOG.warn("server started @ {}:{}\n", ip, port);
    }

    private static void bind(final ServerBootstrap serverBootstrap,final String ip, final int port) {
        serverBootstrap.bind(ip,port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(new Date() + ": 端口[" + port + "]绑定成功!");
            } else {
                System.err.println("端口[" + port + "]绑定失败!");
            }
        });
    }

}
