package com.example.demo.hellonetty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * @author zhaoyu
 * @date 2019-05-17
 *
 */
public class CustomHadler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx,
                                HttpObject httpObject) throws Exception {
        // 获取channel
        Channel channel = ctx.channel();
        // 显示客户端的远程地址
        System.out.println(channel.remoteAddress());
        // 定义发送的数据消息
        ByteBuf content = Unpooled.copiedBuffer("hello netty", CharsetUtil.UTF_8);

        // 构建httpResponse
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);

        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
        // 把响应刷到客户端
        ctx.writeAndFlush(response);
    }
}
