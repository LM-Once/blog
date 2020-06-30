package com.it.utils.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.util.TreeMap;

@Component
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private Logger LOGGER = Logger.getLogger(NettyServerHandler.class);

    private static NettyServerHandler nettyHandler;

    @PostConstruct
    public void init(){
        nettyHandler = this;
    }

    /**
     * 监听客户端发来的数据 msg
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        LOGGER.info("receive client data:"+msg);
        //ctx.pipeline().writeAndFlush("我是服务端");
        //super.channelRead(ctx,msg);
    }

    /**
     * 与客户端建立连接的时候触发
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        InetSocketAddress ipSocket = (InetSocketAddress)ctx.channel().remoteAddress();
        String clientIp = ipSocket.getAddress().getHostAddress();
        LOGGER.info("与客户端建立连接成功，远程地址为："+clientIp);
        super.channelActive(ctx);
    }

    /**
     * @param ctx
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx){
        try {
            InetSocketAddress ipSocket = (InetSocketAddress)ctx.channel().remoteAddress();
            String clientIp = ipSocket.getAddress().getHostAddress();
            LOGGER.info("client is closed,ip:"+clientIp);
            this.updateNodeInfo(clientIp,0);
            ctx.close();
        }catch (Exception e){
            ctx.close();
            LOGGER.info("客户端关闭");
        }
    }

    public void updateNodeInfo (String clientIp,int heartbeatStatus){
        String s = "{simType=-, phoneNumber=-, deviceName=测试机11}";
    }
}
