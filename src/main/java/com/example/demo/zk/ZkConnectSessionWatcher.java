package com.example.demo.zk;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * @author zhaoyu
 * @date 2019-04-23
 * zookeeper 会话重连，恢复之前的会话
 */
public class ZkConnectSessionWatcher implements Watcher {

    public static final String zkServerPath = "127.0.0.1:2181";

    public static final Integer timeout = 5000;

    public static CountDownLatch countDownLatch = null;

    public static void main(String[] args) throws Exception {

        ZooKeeper zk = new ZooKeeper(zkServerPath, timeout, new ZkConnectSessionWatcher());
        long sessionId = zk.getSessionId();
        byte[] sessionPassword = zk.getSessionPasswd();

        System.out.println("客户端开始连接zookeeper服务器...");
        System.out.println("连接状态: {}" + zk.getState());
        Thread.sleep(1000);
        System.out.println("连接状态: {}" + zk.getState());

        Thread.sleep(200);
        System.out.println("开始会话重连...");

        ZooKeeper zkSession = new ZooKeeper(zkServerPath, timeout, new ZkConnectSessionWatcher(), sessionId, sessionPassword);
        System.out.println("重新连接状态zkSession: {}" + zkSession.getState());
        Thread.sleep(1000);
        System.out.println("重新连接状态zkSession: {}" + zkSession.getState());

    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("接收到watcher通知{}" + event);
    }
}
