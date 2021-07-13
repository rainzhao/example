package com.example.demo.zk;

import com.alibaba.fastjson.JSON;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

/**
 * @author zhaoyu
 * @date 2019-04-23
 * <p>
 * zookeeper 获取子节点数据
 */
public class ZkGetChildrenList implements Watcher {

    private ZooKeeper zooKeeper = null;

    public static final String zkServerPath = "172.16.248.32:2181,172.16.248.132:2181,172.16.248.133:2181";

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
        //zkServer.getZooKeeper().getChildren("/imooc", true, new ChildrenCallBack2(), ctx);
        List<String> collect = zkServer.getZooKeeper().getChildren("/dubbo", false).stream()
                .filter(x -> x.contains("com.tuya.edge")).collect(Collectors.toList());
        List<String> children1 = zkServer.getZooKeeper().getChildren("/dubbo/com.tuya.edgegateway.client.IDeviceService/consumers", false);
        List<String> children2 = zkServer.getZooKeeper().getChildren("/dubbo/com.tuya.edgegateway.client.IDeviceService/configurators", false);
        List<String> children3 = zkServer.getZooKeeper().getChildren("/dubbo/com.tuya.edgegateway.client.IDeviceService/routers", false);
        List<String> children4 = zkServer.getZooKeeper().getChildren("/dubbo/com.tuya.edgegateway.client.IDeviceService/providers", false);

        // dubbo%3A%2F%2F172.16.248.20%3A20690%2Fcom.tuya.edgegateway.client.IDeviceService%3Fanyhost%3Dtrue%26application%3Dedge-gateway-proxy%26bean.name%3Dcom.tuya.edgegateway.client.IDeviceService%26deprecated%3Dfalse%26disabled%3Dtrue%26dubbo%3D2.0.2%26dynamic%3Dtrue%26generic%3Dfalse%26interface%3Dcom.tuya.edgegateway.client.IDeviceService%26methods%3DsyncDeviceWithAutoActive%2CissueDeviceCommand%2CdeviceActive%2CqueryDevice%2CqueryDeviceList%26pid%3D1%26release%3D2.7.4-TUYA-SNAPSHOT%26service.filter%3D-exception%26side%3Dprovider%26timeout%3D20000%26timestamp%3D1577238413941
        //dubbo://172.16.248.20:20690/com.tuya.edgegateway.client.IDeviceService?anyhost=true&application=edge-gateway-proxy&bean.name=com.tuya.edgegateway.client.IDeviceService&deprecated=false&disabled=true&dubbo=2.0.2&dynamic=true&generic=false&interface=com.tuya.edgegateway.client.IDeviceService&methods=syncDeviceWithAutoActive,issueDeviceCommand,deviceActive,queryDevice,queryDeviceList&pid=1&release=2.7.4-TUYA-SNAPSHOT&service.filter=-exception&side=provider&timeout=20000&timestamp=1577238413941
//        ["consumers","configurators","routers","providers"]
        System.out.println(JSON.toJSONString(collect));

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
