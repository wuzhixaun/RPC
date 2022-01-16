package com.wuzx.proxy;


import com.alibaba.fastjson.JSON;
import com.wuzx.client.RpcClient;
import com.wuzx.common.RpcRequest;
import com.wuzx.common.RpcResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * 客户端代理类-创建代理对象
 * 1. 封装Request对象
 * 2. 创建RpcClient对象
 * 3. 发送消息，返回结果
 */
public class RpcClientProxy {

    public static Object createProxy(Class serviceClass) {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{serviceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // * 1. 封装Request对象
                RpcRequest request = new RpcRequest();
                request.setRequestId(UUID.randomUUID().toString());
                request.setClassName(method.getDeclaringClass().getName());
                request.setParameterTypes(method.getParameterTypes());
                request.setParameters(args);
                // * 2. 创建RpcClient对象
                RpcClient rpcClient = new RpcClient("127.0.0.1", 6688);

                // * 3. 发送消息
                final Object responseMsg = rpcClient.sendMsg(JSON.toJSONString(request));

                // 4. 返回结果
                final RpcResponse rpcResponse = JSON.parseObject(responseMsg.toString(), RpcResponse.class);

                // 5. 根据消息结果判断是否正常
                if (rpcResponse.getError() != null) {
                    throw new Exception("出现异常");
                }

                // 6. 根据结果转换成对象的类型
                final Object result = rpcResponse.getResult();


                return JSON.parseObject(result.toString(), method.getReturnType());
            }
        });
    }
}
