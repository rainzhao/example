package com.example.demo.zk;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author zhaoyu
 * @date 2019-04-23
 */
public class ZkGetNodeData implements Watcher {

    private ZooKeeper zooKeeper = null;

    public static final String zkServerPath = "127.0.0.1:2181";

    public static final Integer timeout = 5000;

    private static Stat stat = new Stat();

    public ZkGetNodeData() {
    }

    public ZkGetNodeData(String connectString) {
        try {
            zooKeeper = new ZooKeeper(connectString, timeout, new ZkGetNodeData());
        } catch (IOException e) {
            e.printStackTrace();
            if (zooKeeper != null) {
                try {
                    zooKeeper.close();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private static CountDownLatch countDownLatch = null;

    public static void main(String[] args) throws Exception {
        countDownLatch = new CountDownLatch(1);
        ZkGetNodeData zkServer = new ZkGetNodeData(zkServerPath);
        /**
         * stat: 状态
         * path：路径
         */
        byte[] resByte = zkServer.getZooKeeper().getData("/imooc", true, stat);
        String result = new String(resByte);
        System.out.println("当前值:" + result);
        countDownLatch.await();

    }

    @Override
    public void process(WatchedEvent event) {

        try {
            if (event.getType() == Event.EventType.NodeDataChanged) {
                ZkGetNodeData zkServer = new ZkGetNodeData(zkServerPath);
                byte[] resByte = zkServer.getZooKeeper().getData("/imooc", false, stat);
                String result = new String(resByte);
                System.out.println("更该后的值：" + result);
                System.out.println("版本号变化dataVersion:" + stat.getVersion());
                countDownLatch.countDown();
            } else if (event.getType() == Event.EventType.NodeCreated) {

            } else if (event.getType() == Event.EventType.NodeChildrenChanged) {

            } else if (event.getType() == Event.EventType.NodeDeleted) {

            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public ZooKeeper getZooKeeper() {
        return zooKeeper;
    }
}
