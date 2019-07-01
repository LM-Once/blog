package com.it.netty;

import com.it.netty.server.*;
import io.netty.channel.socket.SocketChannel;

import java.util.concurrent.TimeUnit;

public class main {

    public static void main(String[] args) {

        SocketChannel channel=(SocketChannel)NettyChannelMap.get("001");
        System.out.println("新开通一个通道：" +channel);
        System.out.println("clientId：==========="+001);
        if(channel!=null){
            AskMsg askMsg=new AskMsg();
            channel.writeAndFlush(askMsg);
        }

        /*SocketChannel channel=(SocketChannel)NettyChannelMap.get("001");
        System.out.println("新开通一个通道："+channel);
        ReplyServerBody replyBody=new ReplyServerBody("hello");
        ReplyMsg replyMsg=new ReplyMsg();
        replyMsg.setBody(replyBody);

        if(channel!=null){
            System.out.println("通道开通");
            AskMsg askMsg=new AskMsg();
            channel.writeAndFlush(replyMsg);
        }*/
    }
}
