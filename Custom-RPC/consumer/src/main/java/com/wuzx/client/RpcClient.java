package com.wuzx.client;

import com.wuzx.handler.RpcClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 客户端
 * 1. 连接netty服务端
 * 2. 提供调用者主动关闭资源的方法
 * 3. 提供消息发送的方法
 */
public class RpcClient {


    private String ip;
    private int port;

    NioEventLoopGroup bossGroup;
    Channel channel;


    private RpcClientHandler rpcClientHandler =  new RpcClientHandler();;
    private ExecutorService executorService = Executors.newFixedThreadPool(2);

    public RpcClient(String ip, int port) {
        this.ip = ip;
        this.port = port;

        initClient();
    }

    public void initClient() {
        try {
            // 1. 创建线程组
            bossGroup = new NioEventLoopGroup();

            // 2. 创建启动助手
            Bootstrap bootstrap = new Bootstrap();
            // 3. 设置参数
            bootstrap.group(bossGroup)
                    .channel(NioSocketChannel.class)  // 4. 设置通道实现
                    .option(ChannelOption.SO_KEEPALIVE, true) // 5. 设置通道的活跃状态
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)// 6. 设置连接超时时间
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 获取pipeline
                            final ChannelPipeline pipeline = socketChannel.pipeline();

                            // 添加编解码器
                            pipeline.addLast(new StringEncoder());
                            pipeline.addLast(new StringDecoder());

                            // 添加业务处理类
                            pipeline.addLast(rpcClientHandler);

                        }
                    });

            // 7. 连接服务端, 同时将异步改为同步
            channel = bootstrap.connect(ip, port).sync().channel();

        } catch (Exception e) {
            e.printStackTrace();

            // 8. 关闭通道和关闭连接池
            close();
        }


    }


    /**
     * 关闭资源
     */
    public void close() {

        if (null != channel) {
            channel.close();
        }
        if (null != bossGroup) {
            bossGroup.shutdownGracefully();
        }
    }

    /**
     * 发送消息
     */
    public Object sendMsg(String msg) throws ExecutionException, InterruptedException {
        rpcClientHandler.setRequestMsg(msg);
        final Future future = executorService.submit(rpcClientHandler);
        return future.get();
    }



}