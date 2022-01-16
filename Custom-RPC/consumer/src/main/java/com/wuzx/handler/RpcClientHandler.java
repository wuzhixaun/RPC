package com.wuzx.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.Callable;

public class RpcClientHandler extends SimpleChannelInboundHandler<String> implements Callable {

    ChannelHandlerContext ctx;

    // 发送的消息
    private String requestMsg;

    // 服务端消息
    private String responseMsg;

    public void setRequestMsg(String requestMsg) {
        this.requestMsg = requestMsg;
    }

    /**
     * 消息读取就绪事件
     * @param channelHandlerContext
     * @param s
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        responseMsg = s;
        // 唤醒等待线程
        notify();
    }


    /**
     * 发送服务端消息
     *
     * @return
     * @throws Exception
     */
    @Override
    public Object call() throws Exception {
        // 消息发送
        ctx.writeAndFlush(requestMsg);
        // 线程等待
        wait();

        return null;
    }


    /**
     * 通道连接就绪事件
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
    }
}
