package com.example.demo.zk;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * @author zhaoyu
 * @date 2019-04-23
 */
public class ZkTest implements Watcher {

    public static final String zkServerPath = "129.226.119.247:2181";

    public static final Integer timeout = 5000;

    private static CountDownLatch countDownLatch = null;

    public static void main(String[] args) throws Exception{
        countDownLatch = new CountDownLatch(1);
        ZooKeeper zooKeeper = new ZooKeeper(zkServerPath, timeout, new ZkTest());
        System.out.println("客户端开始连接zookeeper服务器...");
        System.out.println("连接状态：{}" + zooKeeper.getState());
        countDownLatch.await();
        System.out.println("连接状态: {}" +  zooKeeper.getState());
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("接收到watcher通知{}" +  event);
        countDownLatch.countDown();
    }
}
