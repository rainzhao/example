package com.example.demo.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author zhaoyu
 * @date 2019-04-23
 * <p>
 * zookeeper 获取子节点数据
 */
public class ZkGetChildrenList implements Watcher {

    private ZooKeeper zooKeeper = null;

    public static final String zkServerPath = "127.0.0.1:2181";

    public static final Integer timeout = 5000;

    public ZkGetChildrenList() {
    }

    public ZkGetChildrenList(String connectString) {
        try {
            zooKeeper = new ZooKeeper(connectString, timeout, new ZkGetChildrenList());
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
        ZkGetChildrenList zkServer = new ZkGetChildrenList(zkServerPath);

        /**
         * path: 父节点路径
         * watch：true 或者 false，注册一个watch事件
         */
//        List<String> childrenList = zkServer.getZooKeeper().getChildren("/imooc", true);
//        for (String s : childrenList) {
//            System.out.println(s);
//        }

        // 异步调用
        String ctx = "{'callback':'ChildrenCallback'}";
//        zkServer.getZooKeeper().getChildren("/imooc", true, new ChildrenCallBack(), ctx);
        zkServer.getZooKeeper().getChildren("/imooc", true, new ChildrenCallBack2(), ctx);
        countDownLatch.await();
    }

    @Override
    public void process(WatchedEvent event) {
        try {
            if (event.getType() == Event.EventType.NodeChildrenChanged) {
                System.out.println("NodeChildrenChanged");
                ZkGetChildrenList zkServer = new ZkGetChildrenList(zkServerPath);
                List<String> childrenList = zkServer.getZooKeeper().getChildren(event.getPath(), false);
                for (String s : childrenList) {
                    System.out.println(s);
                }
                countDownLatch.countDown();
            } else if (event.getType() == Event.EventType.NodeCreated) {
                System.out.println("NodeCreated");
            } else if (event.getType() == Event.EventType.NodeDataChanged) {
                System.out.println("NodeDataChanged");
            } else if (event.getType() == Event.EventType.NodeDeleted) {
                System.out.println("NodeDeleted");
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class ChildrenCallBack implements AsyncCallback.ChildrenCallback {
        @Override
        public void processResult(int rc, String path, Object ctx, List<String> children) {
            for (String child : children) {
                System.out.println(child);
            }
            System.out.println("ChildrenCallBack:" + path);
            System.out.println((String)ctx);
        }
    }

    static class ChildrenCallBack2 implements AsyncCallback.Children2Callback {
        @Override
        public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {
            for (String child : children) {
                System.out.println(child);
            }
            System.out.println("Children2CallBack:" + path);
            System.out.println((String)ctx);
            System.out.println(stat.toString());
        }
    }

    public ZooKeeper getZooKeeper() {
        return zooKeeper;
    }
}
