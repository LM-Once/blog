package com.it.common.netty.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * @author 18576756475
 * @version V1.0
 * @ClassName NettyClientInitializer
 * @Description 客户端初始化
 * @Date 2019-12-12 10:25:56
 **/
public class NettyClientInitializer extends ChannelInitializer<Channel> {

    public NettyClientInitializer(){

    }

    /**
     * 初始化channel
     */
    @Override
    public void initChannel(Channel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        pipeline.addLast(new NettyClientHandler());
    }
}
