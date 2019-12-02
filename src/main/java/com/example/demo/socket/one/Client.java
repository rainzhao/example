package com.example.demo.socket.one;

import java.io.*;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author zhaoyu
 * @date 2019-03-19
 */
public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        socket.setSoTimeout(3000);
        socket.connect(new InetSocketAddress(Inet4Address.getLocalHost(),2000), 3000);
        System.out.println("已发起服务器连接，并进入后续流程");
        System.out.println("客户端信息："+ socket.getLocalAddress() + "P:" + socket.getLocalPort());
        System.out.println("客户端信息："+ socket.getInetAddress() + "P:" + socket.getPort());

        try{
            todo(socket);
        }catch (Exception e) {
            System.out.println("异常关闭");
        }

        socket.close();
        System.out.println("客户端已退出");
    }


    public static void todo(Socket client) throws IOException {
        InputStream in = System.in;
        BufferedReader input = new BufferedReader(new InputStreamReader(in));
        OutputStream outputStream = client.getOutputStream();
        PrintStream socketPrintStream = new PrintStream(outputStream);

        InputStream inputStream = client.getInputStream();
        BufferedReader socketBufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        boolean flag = true;
        do {
            String str = input.readLine();
            // 发送到服务器
            socketPrintStream.println(str);

            // 从服务器读取一行
            String echo = socketBufferedReader.readLine();
            if("bye".equalsIgnoreCase(echo)) {
                flag = false;
            }else {
                System.out.println(echo);
            }
        }while (flag);

        socketPrintStream.close();
        socketBufferedReader.close();

    }
}

