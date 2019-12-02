package com.example.demo.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;

/**
 * @author zhaoyu
 * @date 2019-04-23
 */
public class ZkNodeOperator implements Watcher {

    private ZooKeeper zooKeeper = null;

    public static final String zkServerPath = "127.0.0.1:2181";

    public static final Integer timeout = 5000;

    public ZkNodeOperator() {
    }

    public ZkNodeOperator(String connectString) {
        try {
            zooKeeper = new ZooKeeper(connectString, timeout, new ZkNodeOperator());
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

    /**
     * 创建zk节点
     *
     * @param path 创建的路径
     * @param data 存储数据的byte[]
     * @param acls 控制权限策略
     *             Ids.OPEN_ACL_UNSAFE --> world:anyone:cdrwa
     *             CREATOR_ALL_ACL --> auth:user:password:cdrwa
     *             <p>
     *             同步或异步创建节点，不支持子节点的递归创建，异步有一个callback函数
     *             createMode: 节点类型，是一个枚举
     *             PERSISTENT: 持久节点
     *             PERSISTENT_SEQUENCE: 持久顺序节点
     *             EPHEMERAL: 临时节点
     */
    public void createZkNode(String path, byte[] data, List<ACL> acls) {
        String result = "";
        try {
            // 同步方式
            // result = zooKeeper.create(path, data, acls, CreateMode.EPHEMERAL);

            // 异步方式
            String ctx = "{'create':success}";
            zooKeeper.create(path, data, acls, CreateMode.PERSISTENT, new CreateCallBack(), ctx);

            System.out.println("创建节点：\t" + result + "\t 成功...");
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception{
        ZkNodeOperator zkServer = new ZkNodeOperator(zkServerPath);

        // 创建zk节点
        // zkServer.createZkNode("/testnode", "testnode".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE);

        /**
         * 设置节点数据
         * path： 路径
         * data：数据
         * version：数据状态
         */
        // Stat stat = zkServer.getZooKeeper().setData("/testnode", "xyz".getBytes(), 0);
        // System.out.println(stat.getVersion());

        // 删除节点
        // zkServer.createZkNode("/test-delete-node", "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE);
        // zkServer.getZooKeeper().delete("/test-delete-node", 0);



    }

    @Override
    public void process(WatchedEvent event) {

    }

    class CreateCallBack implements AsyncCallback.StringCallback{
        @Override
        public void processResult(int rc, String path, Object ctx, String name) {
            System.out.println("创建节点:" + path);
            System.out.println((String)ctx);
        }
    }

    public ZooKeeper getZooKeeper() {
        return zooKeeper;
    }
}
