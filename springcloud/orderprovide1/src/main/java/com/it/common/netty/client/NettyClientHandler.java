package com.it.common.netty.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 18576756475
 * @version V1.0
 * @ClassName NettyClientHandler
 * @Description TODO
 * @Date 2019-12-12 11:56:60
 **/
public class NettyClientHandler extends SimpleChannelInboundHandler<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyClientHandler.class);

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) {
        LOGGER.info("收到服务端消息：" + msg);
    }
}
