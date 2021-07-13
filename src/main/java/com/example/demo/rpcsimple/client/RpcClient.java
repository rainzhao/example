package com.example.demo.rpcsimple.client;

import com.example.demo.rpcsimple.common.*;
import com.example.demo.rpcsimple.model.RequestInfo;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class RpcClient {
	private final static Logger LOG = LoggerFactory.getLogger(RpcClient.class);
	private static final String HOST = "127.0.0.1";
	private static final int MAX_RETRY = 5;
	private String ip;
	private int port;
	private Bootstrap bootstrap;
	private EventLoopGroup group;
	private MessageCollector collector;
	private boolean started;
	private boolean stopped;

	private MessageRegistry registry = new MessageRegistry();

	public RpcClient(String ip, int port) {
		this.ip = ip;
		this.port = port;
		this.init();
	}

	public <T> RpcFuture<T> sendAsync(String type, RequestInfo payload) {
		if (!started) {
			connect(MAX_RETRY);
			started = true;
		}
		String requestId = RequestId.next();
		MessageOutput output = new MessageOutput(requestId, type, payload);
		return collector.send(output);
	}

	public void sendTest(String type, RequestInfo payload) {
		if (!started) {
			connect(MAX_RETRY);
			started = true;
		}
		String requestId = RequestId.next();
		MessageOutput output = new MessageOutput(requestId, type, payload);
		collector.sendAsync(output);
	}

	public void init() {
		bootstrap = new Bootstrap();
		group = new NioEventLoopGroup(1);
		bootstrap.group(group);
		collector = new MessageCollector();
		bootstrap.channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline pipe = ch.pipeline();
				//pipe.addLast("codec", new HttpServerCodec());
				//pipe.addLast("aggregator", new HttpObjectAggregator(512 * 1024));
				pipe.addLast(new Spliter());
				pipe.addLast(new MessageDecoder());
				pipe.addLast(new MessageEncoder());
				//pipe.addLast(new FixedLengthFrameDecoder(44));
				pipe.addLast(collector);

//				ch.pipeline().addLast(new Spliter());
//				ch.pipeline().addLast(new PacketDecoder());
//				pipe.addLast(collector);
//				ch.pipeline().addLast(new PacketEncoder());
			}

		});
		bootstrap.option(ChannelOption.TCP_NODELAY, true).option(ChannelOption.SO_KEEPALIVE, true);
		this.connect(MAX_RETRY);
	}

	public void connect(int retry) {
		bootstrap.connect(ip, port).addListener(future -> {
			if (future.isSuccess()) {
				System.out.println(new Date() + ": 连接成功，启动控制台线程……");
				Channel channel = ((ChannelFuture) future).channel();
				channel.writeAndFlush("1234");
			} else if (retry == 0) {
				System.err.println("重试次数已用完，放弃连接！");
			} else {
				// 第几次重连
				int order = (MAX_RETRY - retry) + 1;
				// 本次重连的间隔
				int delay = 1 << order;
				System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
				bootstrap.config().group().schedule(() -> connect(retry - 1), delay, TimeUnit
						.SECONDS);
			}
		});
	}

	public void reconnect() {
		if (stopped) {
			return;
		}
		bootstrap.connect(ip, port).addListener(future -> {
			if (future.isSuccess()) {
				return;
			}
			if (!stopped) {
				group.schedule(() -> {
					reconnect();
				}, 1, TimeUnit.SECONDS);
			}
		});
	}

	public void close() {
		stopped = true;
		group.shutdownGracefully(0, 5000, TimeUnit.SECONDS);
	}

}
