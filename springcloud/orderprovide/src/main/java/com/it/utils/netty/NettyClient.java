package com.it.utils.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.apache.log4j.Logger;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class NettyClient {
    private static final String HOST = "127.0.0.1";

    private static final int PORT = 9002;

    private static NettyClient nettyClient;

    private static final Logger LOGGER = Logger.getLogger(NettyClient.class.getName());

    private NettyClient() {
    }

    public static NettyClient getInstance() {
        if (nettyClient == null) {
            nettyClient = new NettyClient();
        }
        return nettyClient;
    }

    public void run(Map<String, Object> params) {
        {

            Bootstrap bootstrap = new Bootstrap();
            // 指定channel[通道]类型
            bootstrap.channel(NioSocketChannel.class);
            // 指定Handler
            bootstrap.handler(new ChannelInitializer<Channel>() {
                @Override
                protected void initChannel(Channel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    /*
                     * 这个地方的必须和服务端对应上。否则无法正常解码和编码
                     */

                    pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
                    pipeline.addLast("decoder", new StringDecoder());
                    pipeline.addLast("encoder", new StringEncoder());

                    pipeline.addLast(new NettyClientHandler(params));
                }
            });
            bootstrap.group(new NioEventLoopGroup());

            ChannelFuture connect = bootstrap.connect(new InetSocketAddress(HOST, PORT));
            if (connect.isSuccess()) {
                LOGGER.info("client connect success");
            }
        }
    }

    public static void main(String args[]) {
        do {
            Integer count = 11;
        } while (true);
    }
}