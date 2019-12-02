package com.example.demo.socket.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.UUID;

/**
 * @author zhaoyu
 * @date 2019-03-20
 */
public class UDPProviderNew {

    public static void main(String[] args) throws IOException {
        // 生成唯一标识
        String sn = UUID.randomUUID().toString();

        Provider provider = new Provider(sn);
        provider.start();

        // 读取任意
        System.in.read();
        provider.exit();
    }


    private static class Provider extends Thread {
        private final String sn;

        private boolean done = false;

        private DatagramSocket ds = null;

        public Provider(String sn) {
            this.sn = sn;
        }

        @Override
        public void run() {
            System.out.println("UDPProvider started.");
            try {
                // 作为接收者，指定一个端口用于数据接收
                ds = new DatagramSocket(20000);


                while (!done) {

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
                    System.out.println("UDPProvider receive from ip: " + ip + "\t port:" + port + "\tdata:" + data);

                    int responsePort = MessageCreator.parsePort(data);
                    if (responsePort != -1) {
                        // 构建一份回送数据
                        String responseData = MessageCreator.buildWithSn(sn);
                        byte[] responseDataBytes = responseData.getBytes();
                        // 直接根据发送者构建一份回送信息
                        DatagramPacket responsePacket = new DatagramPacket(responseDataBytes,
                                responseDataBytes.length,
                                receivePack.getAddress(),
                                responsePort);

                        ds.send(responsePacket);
                    }


                }
            } catch (Exception ignored) {

            } finally {
                close();
            }
            //完成
            System.out.println(" UDPProvider finished");
        }

        private void close() {
            if (ds != null) {
                ds.close();
                ds = null;
            }
        }

        void exit() {
            done = true;
            close();
        }
    }


}
