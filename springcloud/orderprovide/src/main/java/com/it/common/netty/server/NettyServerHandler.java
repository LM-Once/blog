package com.it.common.netty.server;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.InetSocketAddress;
import java.util.Map;

/**
 * @author 18576756475
 * @version V1.0
 * @ClassName NettyServerHandler
 * @Description TODO
 * @Date 2019-12-12 10:21:23
 **/
public class NettyServerHandler extends SimpleChannelInboundHandler<String> {

    /** 日志 */
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyServerHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        StringBuilder sb = null;
        Map<String, Object> result = null;
        try {
            // 报文解析处理
            sb = new StringBuilder();
            result = JSON.parseObject(msg);
            sb.append(result);
            sb.append("解析成功");
            sb.append("\n");
            ctx.writeAndFlush(sb);
        } catch (Exception e) {
            String errorCode = "-1\n";
            ctx.writeAndFlush(errorCode);
            LOGGER.error("报文解析失败: " + e.getMessage());
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = insocket.getAddress().getHostAddress();
        LOGGER.info("收到客户端[ip:" + clientIp + "]连接");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 当出现异常就关闭连接
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = insocket.getAddress().getHostAddress();
        LOGGER.info("客户端[ip:" + clientIp + "]连接出现异常，服务器主动关闭连接。。。");
        ctx.close();
    }
}
