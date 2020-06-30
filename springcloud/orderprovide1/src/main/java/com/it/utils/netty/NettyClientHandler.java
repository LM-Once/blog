package com.it.utils.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = Logger.getLogger(NettyClientHandler.class.getName());

    private  Map<String, Object> map;

    public NettyClientHandler(Map<String, Object> param){
        this.map = param;
    }

    /*
     * 监听服务器发送来的数据
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        LOGGER.info("监听服务端发过来的数据为");
        System.out.println("监听服务端发过来的数据为：" + msg.toString());
    }

    /*
     *  启动客户端时触发
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端已启动！");
        System.out.println(map.get("user"));
        ctx.writeAndFlush("我是 client " + new Date() + "\n");
        super.channelActive(ctx);
    }

    /*
     * 关闭客户端 触发
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端已关闭");
        //重新连接服务器
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                NettyClient.getInstance().run(null);
            }
        }, 2, TimeUnit.SECONDS);
        ctx.close();
        super.channelInactive(ctx);
    }
}
