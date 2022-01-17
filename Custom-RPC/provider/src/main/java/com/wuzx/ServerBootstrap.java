package com.wuzx;

import com.wuzx.server.RpcServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wuzhixuan
 * @version 1.0.0
 * @ClassName ServerBootstrap.java
 * @Description 服务端启动类
 * @createTime 2022年01月17日 16:00:00
 */
@SpringBootApplication
public class ServerBootstrap implements CommandLineRunner {

    @Autowired
    RpcServer rpcServer;

    public static void main(String[] args) {
        SpringApplication.run(ServerBootstrap.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                rpcServer.startServer("127.0.0.1", 6688);
            }
        }).start();
    }
}
