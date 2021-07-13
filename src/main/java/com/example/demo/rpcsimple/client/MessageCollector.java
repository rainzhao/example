package com.example.demo.rpcsimple.client;

import com.alibaba.fastjson.JSON;
import com.example.demo.rpcsimple.common.MessageInput;
import com.example.demo.rpcsimple.common.MessageOutput;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

@Sharable
public class MessageCollector extends ChannelInboundHandlerAdapter {

	private final static Logger LOG = LoggerFactory.getLogger(MessageCollector.class);
	private ConcurrentMap<String, RpcFuture<?>> pendingTasks = new ConcurrentHashMap<>();

	private ConcurrentMap<String, Promise<MessageInput>> promiseConcurrentMap = new ConcurrentHashMap<>();
	private Throwable ConnectionClosed = new Exception("rpc connection not active error");

	private RpcClient client;
	private ChannelHandlerContext context;

	public MessageCollector() {
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		this.context = ctx;
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		this.context = null;
		pendingTasks.forEach((__, future) -> {
			future.fail(ConnectionClosed);
		});
		pendingTasks.clear();
		// 尝试重连
		ctx.channel().eventLoop().schedule(() -> {
			client.reconnect();
		}, 1, TimeUnit.SECONDS);
	}

	public <T> RpcFuture<T> send(MessageOutput output) {
		ChannelHandlerContext ctx = context;
		RpcFuture<T> future = new RpcFuture<T>();
		if (ctx != null) {
			ctx.channel().eventLoop().execute(() -> {
				ctx.writeAndFlush(output);
				pendingTasks.put(output.getRequestId(), future);
			});
		} else {
			future.fail(ConnectionClosed);
		}
		return future;
	}

	public void sendAsync(MessageOutput output) {
		Promise<MessageInput> agent = new DefaultPromise<>(context.executor());
		agent.addListener(future -> {
			Object object = future.get();
			System.out.println(object);
		});
		promiseConcurrentMap.put(output.getRequestId(), agent);
		context.writeAndFlush(output);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (!(msg instanceof MessageInput)) {
			return;
		}
		MessageInput input = (MessageInput) msg;
//		String payload = input.getPayload();
//		RpcFuture<Object> future = (RpcFuture<Object>) pendingTasks.remove(input.getRequestId());
//		future.success(input.getPayload());

		Promise<MessageInput> remove = promiseConcurrentMap.remove(input.getRequestId());
		if (remove != null) {
			remove.trySuccess(input);
		}
	}

	public void channelRead11(ChannelHandlerContext ctx, Object msg) throws Exception {

		System.out.println("回传了。。。。。。。。。。。。");

		if (!(msg instanceof MessageInput)) {
			return;
		}
		MessageInput input = (MessageInput) msg;

		// 业务逻辑在这里
//		Class<?> clazz = registry.get(input.getType());
//		if (clazz == null) {
//			LOG.error("unrecognized msg type {}", input.getType());
//			return;
//		}
//		Object o = input.getPayload(clazz);
//		System.out.println(JSON.toJSONString(o));
//		// 读操作后需要进行释放****
//		ReferenceCountUtil.release(msg);
	}

	public void close() {
		ChannelHandlerContext ctx = context;
		if (ctx != null) {
			ctx.close();
		}
	}

}
