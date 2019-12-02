package com.example.demo.rpcsimple.common;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

//@Sharable
//public class MessageEncoder extends MessageToMessageEncoder<MessageOutput> {
//
//	@Override
//	protected void encode(ChannelHandlerContext ctx, MessageOutput msg, List<Object> out) throws Exception {
//		ByteBuf buf = PooledByteBufAllocator.DEFAULT.directBuffer();
//		writeStr(buf, msg.getRequestId());
//		writeStr(buf, msg.getType());
//		writeStr(buf, JSON.toJSONString(msg.getPayload()));
//		out.add(buf);
//	}
//
//	private void writeStr(ByteBuf buf, String s) {
//		buf.writeInt(s.length());
//		buf.writeBytes(s.getBytes(Charsets.UTF8));
//	}
//
//}

@Sharable
public class MessageEncoder extends MessageToByteEncoder<MessageOutput> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MessageOutput msg, ByteBuf byteBuf) throws Exception {
        ByteBuf buf = PooledByteBufAllocator.DEFAULT.directBuffer();
		writeStr(buf, msg.getRequestId());
		writeStr(buf, msg.getType());
		writeStr(buf, JSON.toJSONString(msg.getPayload()));
		byteBuf.writeBytes(buf);
	}

	private void writeStr(ByteBuf buf, String s) {
		buf.writeInt(s.length());
		buf.writeBytes(s.getBytes(Charsets.UTF8));
	}
}
