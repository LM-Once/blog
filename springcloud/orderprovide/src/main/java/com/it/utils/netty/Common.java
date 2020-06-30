package com.it.utils.netty;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Common {
    private static final int PORT = 19800;

    private static final String HOST = "127.0.0.1";

    public static String socketClient(String message) {
        Socket socket = null;
        StringBuilder sb = null;
        try {
            socket = new Socket(HOST, PORT);

            OutputStream outputStream = socket.getOutputStream();

            socket.getOutputStream().write(message.getBytes("GBK"));

            socket.shutdownOutput();

            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int len;
            sb = new StringBuilder();
            while ((len = inputStream.read(bytes)) != -1) {
                sb.append(new String(bytes, 0, len, "UTF-8"));
            }
            inputStream.close();
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Common.socketClient("test hehe");
    }
}
