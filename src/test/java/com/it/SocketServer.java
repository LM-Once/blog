package com.it;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

    public static void server(){
        try {
            // 监听指定的端口
            int port = 55533;
            ServerSocket server = new ServerSocket(port);

            // server将一直等待连接的到来
            System.out.println("server等待连接的到来");
            Socket socket = server.accept();
            // 建立好连接后，从socket中获取输入流，并建立缓冲区进行读取
            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int len;
            StringBuilder sb = new StringBuilder();
            while ((len = inputStream.read(bytes)) != -1) {

                sb.append(new String(bytes, 0, len,"UTF-8"));
            }
            System.out.println("get message from client: " + sb);
            inputStream.close();
            socket.close();
            server.close();
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception{
        SocketServer.server();
    }
}
