package com.it.common.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 18576756475
 * @version V1.0
 * @ClassName NettyClient
 * @Description netty 客户端
 * @Date 2019-12-12 11:20:20
 **/
public class NettyClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyClient.class);
    private String host;

    private int port;

    private Channel channel;

    public NettyClient(String host, int port){
        this.host = host;
        this.port = port;
    }

    /**
     * 连接方法
     */
    public void connect() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.TCP_NODELAY, true);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.handler(new NettyClientInitializer());
            channel = bootstrap.connect(host, port).sync().channel();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    /**
     * 发送消息事件
     * @param msg
     */
    public void sendMsgEvent(Object msg){
        if (channel == null ){
            LOGGER.info("log -> client to server is not fail");
        }
        /**
         * 客户端向服务端发送消息
         */
        channel.writeAndFlush(msg);
    }

    /**
     * 关闭通道
     */
    public void closeFuture(){
        if (channel != null ){
            try {
                channel.closeFuture().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 测试入口
     *
     * @param args
     */
    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 8085;
        NettyClient nettyClient = new NettyClient(host, port);
        nettyClient.connect();
        nettyClient.sendMsgEvent("{\"name\":\"admin\",\"age\":27}\n");
        nettyClient.closeFuture();
    }
}
