package com.example.demo.rpcsimple.server;

import com.alibaba.fastjson.JSON;
import com.example.demo.rpcsimple.common.MessageHandlers;
import com.example.demo.rpcsimple.common.MessageInput;
import com.example.demo.rpcsimple.common.MessageOutput;
import com.example.demo.rpcsimple.common.MessageRegistry;
import com.example.demo.rpcsimple.model.RequestInfo;
import com.example.demo.rpcsimple.util.ApplicationContextUtils;
import com.example.demo.rpcsimple.util.ContextResolverUtils;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.example.demo.rpcsimple.common.ContextConf.getRegistServerMap;

@Sharable
public class MessageCollector extends ChannelInboundHandlerAdapter {

    private final static Logger LOG = LoggerFactory.getLogger(MessageCollector.class);

    private ThreadPoolExecutor executor;

    public MessageCollector(int workerThreads) {
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(1000);
        ThreadFactory factory = new ThreadFactory() {

            AtomicInteger seq = new AtomicInteger();

            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("rpc-" + seq.getAndIncrement());
                return t;
            }

        };
        this.executor = new ThreadPoolExecutor(1, workerThreads, 30, TimeUnit.SECONDS, queue, factory,
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOG.debug("connection comes");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof MessageInput) {
            this.executor.execute(() -> {
                this.handleMessage(ctx, (MessageInput) msg);
            });
        }
    }

    private void handleMessage(ChannelHandlerContext ctx, MessageInput input) {
        try {
            // 业务逻辑在这里
            RequestInfo payload = input.getPayload(RequestInfo.class);
            // 序列化参数传递过来的方法名
            String methodName = payload.getMethodName();
            // 序列化参数传递过来的接口名
            String interfaceName = payload.getInterfaceName();
            // 请求的参数类型列表
            List<String> paramsTypeList = payload.getParams();
            // 请求参数列表
            Object[] params = payload.getArgs();

            ApplicationContext applicationContext = ApplicationContextUtils.applicationContext;

            ConcurrentHashMap<String, Class> registServerMap = getRegistServerMap();

            Class interfaceClass = registServerMap.get(interfaceName);

            System.out.println("methodName:" + methodName + "interface: " + interfaceName);
            Object bean = applicationContext.getBean(interfaceClass);

            //Method declaredMethod = bean.getClass().getDeclaredMethod(methodName);

            try {
                Method method = ContextResolverUtils.getMethod(methodName, interfaceName, params, bean.getClass());
                Object data = null;
                if (params != null && params.length > 0) {
                    data = method.invoke(bean, params);
                } else {
                    data = method.invoke(bean);
                }

                System.out.println(data);
                MessageOutput messageOutput = new MessageOutput();
                messageOutput.setType("type");
                RequestInfo requestInfo = new RequestInfo();
                requestInfo.setData(JSON.toJSONString(data));
                messageOutput.setPayload(requestInfo);
                messageOutput.setRequestId(input.getRequestId());
                ctx.channel().writeAndFlush(messageOutput);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOG.warn("connection error", cause);
    }

}
