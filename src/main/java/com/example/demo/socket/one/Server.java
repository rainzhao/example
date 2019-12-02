package com.example.demo.socket.one;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * @author zhaoyu
 * @date 2019-03-19
 */
public class Server {


    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(2000);


        System.out.println("服务器准备就绪");
        System.out.println("服务器信息：" + serverSocket.getInetAddress() + " P :" + serverSocket.getLocalPort());

        //等待客户端连接


        for (; ; ) {
            // 得到客户端
            Socket client = serverSocket.accept();

            ClientHandler clientHandler = new ClientHandler(client);

            clientHandler.start();
        }

    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private boolean flag = true;

        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            System.out.println("新客户端信息：" + socket.getInetAddress() + " P :" + socket.getPort());
            try {
                // 得到打印流，用于数据输出，服务器会送数据使用
                PrintStream socketOutput = new PrintStream(socket.getOutputStream());
                BufferedReader socketInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                do{
                    String str = socketInput.readLine();
                    if("bye".equalsIgnoreCase(str)) {
                        flag = false;
                        socketOutput.println("bye//");
                    }else{
                        System.out.println(str);
                        socketOutput.println("回送：" + str);
                    }

                }while(flag);

            } catch (Exception ex) {
                System.out.println("连接异常");
            }finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
