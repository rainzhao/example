package com.example.demo.socket.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;

/**
 * @author zhaoyu
 * @date 2019-03-20
 *
 * UDP 搜索者
 */
public class UDPSearcher {
    public static void main(String[] args) throws IOException {
        System.out.println("UDPSearcher started.");
        // 作为搜索方，让系统自动分配端口
        DatagramSocket ds = new DatagramSocket();

        // 构建一份回送数据

        String requestData = "Hello world ! ";
        byte[] requestDataBytes = requestData.getBytes();
        // 直接根据发送者构建一份请求数据
        DatagramPacket requestPacket = new DatagramPacket(requestDataBytes,
                requestDataBytes.length);
        requestPacket.setAddress(Inet4Address.getLocalHost());
        requestPacket.setPort(20000);
        // 发送
        ds.send(requestPacket);

        final byte[] buf = new byte[512];
        DatagramPacket receivePack = new DatagramPacket(buf, buf.length);

        // 接收
        ds.receive(receivePack);

        // 打印接收到的信息与发送者的信息
        // 发送者的IP地址
        String ip = receivePack.getAddress().getHostAddress();
        // 发送者的端口
        int port = receivePack.getPort();

        int dataLen = receivePack.getLength();
        String data = new String(receivePack.getData(), 0, dataLen);
        System.out.println("UDPSearcher receive from ip: " + ip + "\t port:" + port + "\tdata:" + data );



        //完成
        System.out.println(" UDPSearcher finished");
        ds.close();
    }
}
