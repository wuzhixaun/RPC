package com.wuzx.handler;

import com.alibaba.fastjson.JSON;
import com.wuzx.annotation.RpcService;
import com.wuzx.common.RpcRequest;
import com.wuzx.common.RpcResponse;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.BeansException;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wuzhixuan
 * @version 1.0.0
 * @ClassName RpcServerHandler.java
 * @Description 服务业务处理类
 * @createTime 2022年01月13日 17:39:00
 */
@Component
@ChannelHandler.Sharable // 使这个handler 可以重复使用
public class RpcServerHandler extends SimpleChannelInboundHandler<String> implements ApplicationContextAware {

    private static final Map SERVICE_INSTANCE_MAP = new ConcurrentHashMap();


    /**
     * 通道读取就绪时间
     *
     * @param channelHandlerContext
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        // 1.接收客户端请求- 将msg转化RpcRequest对象
        RpcRequest rpcRequest = JSON.parseObject(msg, RpcRequest.class);
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setRequestId(rpcRequest.getRequestId());
        try {
            rpcResponse.setResult(handler(rpcRequest));
        } catch (Exception exception) {
            exception.printStackTrace();
            rpcResponse.setError(exception.getMessage());
        }

        //6.给客户端进行响应
        channelHandlerContext.writeAndFlush(JSON.toJSONString(rpcResponse));
    }

    private Object handler(RpcRequest rpcRequest) throws InvocationTargetException {
        // 3.根据传递过来的beanName从缓存中查找到对应的bean
        Object serviceBean = SERVICE_INSTANCE_MAP.get(rpcRequest.getClassName());
        if (serviceBean == null) {
            throw new RuntimeException("根据beanName找不到服务,beanName:" + rpcRequest.getClassName());
        }
        //4.解析请求中的方法名称. 参数类型 参数信息
        Class<?> serviceBeanClass = serviceBean.getClass();
        String methodName = rpcRequest.getMethodName();
        Class<?>[] parameterTypes = rpcRequest.getParameterTypes();
        Object[] parameters = rpcRequest.getParameters();
        //5.反射调用bean的方法- CGLIB反射调用
        FastClass fastClass = FastClass.create(serviceBeanClass);
        FastMethod method = fastClass.getMethod(methodName, parameterTypes);
        return method.invoke(serviceBean, parameters);
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

        for (String key : rpcServiceMap.keySet()) {
            Object serviceBean = rpcServiceMap.get(key);
            if (serviceBean.getClass().getInterfaces().length == 0) {
                throw new RuntimeException("服务必须实现接口");
            }

            // 默认取第一个接口作为缓存bean的名称
            String name = serviceBean.getClass().getInterfaces()[0].getName();
            SERVICE_INSTANCE_MAP.put(name, serviceBean);
        }
    }
}
