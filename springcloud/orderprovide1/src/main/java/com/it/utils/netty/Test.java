package com.it.utils.netty;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Test {

    public static void main(String[] args) {
        try {
            StringBuilder sb=null;
            int port = 19800;
            ServerSocket server = new ServerSocket(port);
            Socket socket = server.accept();
            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int len;
            sb = new StringBuilder();
            while ((len = inputStream.read(bytes)) != -1) {
                sb.append(new String(bytes, 0, len,"UTF-8"));
            }
            System.out.println(sb.toString());
            inputStream.close();
            socket.close();
            server.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
