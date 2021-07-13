package com.example.demo.zk;

import org.apache.zookeeper.*;

/**
 * @author zhaoyu
 * @date 2019/12/31
 */

public class ZookeeperAuth2{
    //连接地址
    //private static final String CONNECTION_ADDR = "192.168.1.101:2181,192.168.1.102:2181,192.168.1.103:2181";

    private static final String CONNECTION_ADDR = "172.16.248.32:2181,172.16.248.132:2181,172.16.248.133:2181";
    //测试路径
    private static final String NODE_PATH = "/";
    //认证类型
    private static final String AUTH_TYPE = "digest";
    //正确的认证
    private static final String AUTH_VALUE = "20190112";
    //错误的认证
    private static final String AUTH_BAD_VALUE = "20180112";
    public static void main(String[] args) {
        /*try {
            //一、创建节点
            ZooKeeper zk=new ZooKeeper(CONNECTION_ADDR,5000,new Watcher() {
                public void process(WatchedEvent event) {
                }
            });
            zk.addAuthInfo(AUTH_TYPE,AUTH_VALUE.getBytes());
            zk.create(NODE_PATH,"买女孩的小火柴".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
        } catch (Exception e) {
            System.err.println("节点创建失败，原因：" + e.getMessage());
        }

        try {
            //二、无授权信息访问
            ZooKeeper zk2=new ZooKeeper(CONNECTION_ADDR,5000,new Watcher() {
                public void process(WatchedEvent event) {
                }
            });
            System.out.println("无授权信息访问，获取数据为：" + new String(zk2.getData(NODE_PATH,false,null)));
        } catch (Exception e) {
            System.err.println("无授权信息访问失败，原因：" + e.getMessage());
        }
        try {
            //三、使用错误信息访问结点
            ZooKeeper zk3=new ZooKeeper(CONNECTION_ADDR,5000,new Watcher() {
                public void process(WatchedEvent event) {
                }
            });
            zk3.addAuthInfo(AUTH_TYPE,AUTH_BAD_VALUE.getBytes());
            System.out.println("使用错误信息访问结点，获取数据为：" + new String(zk3.getData(NODE_PATH,false,null)));
        } catch (Exception e) {
            System.err.println("使用错误信息访问结点失败，原因：" + e.getMessage());
        }*/
        try {
            //四、使用正确信息访问结点
            ZooKeeper zk4=new ZooKeeper(CONNECTION_ADDR,5000,new Watcher() {
                public void process(WatchedEvent event) {
                }
            });
            //zk4.addAuthInfo(AUTH_TYPE,AUTH_VALUE.getBytes());
            System.out.println("使用正确信息访问结点，获取数据为：" + new String(zk4.getData(NODE_PATH,false,null)));

        } catch (Exception e) {
            System.err.println("使用正确信息访问结点失败，原因：" + e.getMessage());
        }
    }

}
