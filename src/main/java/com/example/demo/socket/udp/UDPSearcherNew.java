package com.example.demo.socket.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author zhaoyu
 * @date 2019-03-21
 */
public class UDPSearcherNew {

    private static final int LISTEN_PORT = 30000;

    public static void main(String[] args) throws Exception {
        Listener listener = listen();
        sendBroadCast();

        System.in.read();
        List<Device> devicesAndClose = listener.getDevicesAndClose();
        for (Device device : devicesAndClose) {
            System.out.println("device:" + device.toString());
        }
    }

    private static Listener listen() throws Exception{
        System.out.println("开始监听");
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Listener listener = new Listener(LISTEN_PORT, countDownLatch);
        listener.start();

        countDownLatch.await();

        return listener;
    }

    private static void sendBroadCast() throws IOException {
        System.out.println("UDPSearcher sendBroadcast started");
        DatagramSocket ds = new DatagramSocket();
        String requestData = MessageCreator.buildWithPort(LISTEN_PORT);
        byte[] requestDataBytes = requestData.getBytes();
        DatagramPacket requestPacket = new DatagramPacket(requestDataBytes, requestDataBytes.length);
        requestPacket.setPort(20000);
        requestPacket.setAddress(Inet4Address.getByName("255.255.255.255"));
        ds.send(requestPacket);
        ds.close();
        System.out.println("UpdSearcher sendBroadcast finish");
    }

    private static class Device {
        private String sn;
        private int port;
        private String ip;

        public Device(String sn, int port, String ip) {
            this.sn = sn;
            this.port = port;
            this.ip = ip;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        @Override
        public String toString() {
            return "Device{" +
                    "sn='" + sn + '\'' +
                    ", port=" + port +
                    ", ip='" + ip + '\'' +
                    '}';
        }
    }

    private static class Listener extends Thread {
        private final int listenPort;

        private final CountDownLatch countDownLatch;

        private final List<Device> devices = new ArrayList<>();

        private boolean done = false;

        private DatagramSocket ds = null;

        public Listener(int listenPort, CountDownLatch countDownLatch) {
            this.listenPort = listenPort;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                countDownLatch.countDown();
                ds = new DatagramSocket(listenPort);
                while (!done) {
                    // 构建接收实体
                    final byte[] buf = new byte[512];
                    DatagramPacket receivePack = new DatagramPacket(buf, buf.length);
                    ds.receive(receivePack);
                    // 打印接收到的信息与发送者的信息
                    // 发送者的IP地址
                    String ip = receivePack.getAddress().getHostAddress();
                    int port = receivePack.getPort();
                    int dataLen = receivePack.getLength();
                    String data = new String(receivePack.getData(), 0, dataLen);

                    System.out.println("UDP Searcher receive from ip:" + ip + "\t port:" + port + "\t data" + data);

                    String sn = MessageCreator.parseSn(data);
                    if (sn != null) {
                        Device device = new Device(sn, port, ip);
                        devices.add(device);
                    }
                }
            } catch (Exception ignored) {

            } finally {
                close();
            }
            System.out.println("UDPSearcher listener finished");
        }

        private void close() {
            if (ds != null) {
                ds.close();
                ds = null;
            }
        }

        List<Device> getDevicesAndClose() {
            done = true;
            close();
            return devices;
        }
    }

}
