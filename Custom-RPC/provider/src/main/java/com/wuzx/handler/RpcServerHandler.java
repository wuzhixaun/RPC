package com.wuzx.handler;

import com.wuzx.annotation.RpcService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

/**
 * @author wuzhixuan
 * @version 1.0.0
 * @ClassName RpcServerHandler.java
 * @Description 服务业务处理类
 * @createTime 2022年01月13日 17:39:00
 */
@Component
public class RpcServerHandler extends SimpleChannelInboundHandler<String> implements ApplicationContextAware {

    /**
     * 通道读取就绪时间
     *
     * @param channelHandlerContext
     * @param s
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {

    }


    /**
     * 将标有@RpcService注解的bean缓存
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 从上下问获取有RpcService的类
        final Map<String, Object> rpcServiceMap = applicationContext.getBeansWithAnnotation(RpcService.class);

        if (null == rpcServiceMap || rpcServiceMap.size() == 0) {
            return;
        }



    }
}
