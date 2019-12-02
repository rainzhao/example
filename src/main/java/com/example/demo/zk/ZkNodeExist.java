package com.example.demo.zk;

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
public class ZkNodeExist implements Watcher {

    private ZooKeeper zooKeeper = null;

    public static final String zkServerPath = "127.0.0.1:2181";

    public static final Integer timeout = 5000;

    public ZkNodeExist() {
    }

    public ZkNodeExist(String connectString) {
        try {
            zooKeeper = new ZooKeeper(connectString, timeout, new ZkNodeExist());
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

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        ZkNodeExist zkServer = new ZkNodeExist(zkServerPath);

        /**
         * path：节点路径
         * watch：是否监听
         */
        Stat stat = zkServer.getZooKeeper().exists("/imooc", true);
        if (stat != null) {
            System.out.println("查询的节点版本为dataVersion：" + stat.getVersion());
        } else {
            System.out.println("该节点不存在...");
        }

        countDownLatch.await();
    }


    @Override
    public void process(WatchedEvent event) {
        if (event.getType() == Event.EventType.NodeCreated) {
            System.out.println("节点创建");
            countDownLatch.countDown();
        } else if (event.getType() == Event.EventType.NodeDataChanged) {
            System.out.println("节点数据改变");
            countDownLatch.countDown();
        } else if (event.getType() == Event.EventType.NodeDeleted) {
            System.out.println("节点删除");
            countDownLatch.countDown();
        }
    }

    public ZooKeeper getZooKeeper() {
        return zooKeeper;
    }
}
