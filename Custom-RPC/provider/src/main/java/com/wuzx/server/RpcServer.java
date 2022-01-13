package com.wuzx.server;

import com.wuzx.handler.RpcServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wuzhixuan
 * @version 1.0.0
 * @ClassName RpcServer.java
 * @Description 服务Netty启动类
 * @createTime 2022年01月13日 17:27:00
 */
@Service
public class RpcServer implements DisposableBean {

    private NioEventLoopGroup bossGroup;
    private NioEventLoopGroup workerGroup;


    @Autowired
    private RpcServerHandler rpcServerHandler;


    public void startServer(String ip, int port) {
        try {
            // 1.创建boss、worker线程
            bossGroup = new NioEventLoopGroup(1);
            workerGroup = new NioEventLoopGroup();

            // 2.创建服务启动助手
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            // 3.设置参数
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // 设置通道的实现方式
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {

                            final ChannelPipeline pipeline = socketChannel.pipeline();


                            // 添加String的编解
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());

                            // 添加自定义业务处理类
                            pipeline.addLast(rpcServerHandler);
                        }
                    });

            // 4.绑定端口
            final ChannelFuture sync = serverBootstrap.bind(ip, port).sync();

            // 5.打印日志

            System.out.println("==========服务端启动成功==========");

            // 6.设置关闭时间通知
            sync.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            shutDown();
        }



    }


    @Override
    public void destroy() throws Exception {
        shutDown();
    }

    private void shutDown() {
        if (null != bossGroup) {
            bossGroup.shutdownGracefully();
        }

        if (null != workerGroup) {
            bossGroup.shutdownGracefully();
        }
    }
}
