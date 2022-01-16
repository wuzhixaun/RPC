package com.wuzx.common;

import lombok.Data;

/**
 * @author wuzhixuan
 * @version 1.0.0
 * @ClassName RpcRequest.java
 * @Description 封装的请求对象
 * @createTime 2022年01月13日 17:12:00
 */
@Data
public class RpcRequest {


    /**
     * 请求对象的ID
     */
    private String requestId;
    /**
     * 类名
     */
    private String className;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 参数类型
     */
    private Class<?>[] parameterTypes;
    /**
     * 入参
     */
    private Object[] parameters;
}
