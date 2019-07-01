package com.it.netty.server;

public class PingMsg extends BaseMsg {

    public PingMsg() {
        super();
        setType(MsgType.PING);
    }
}
