package com.wuzx.common;

import lombok.Data;

/**
 * @author wuzhixuan
 * @version 1.0.0
 * @ClassName RpcResponse.java
 * @Description 封装的响应对象
 * @createTime 2022年01月13日 17:12:00
 */
@Data
public class RpcResponse {
    /**
     * 响应ID
     */
    private String requestId;

    /**
     * 错误信息
     */
    private String error;

    /**
     * 返回的结果
     */
    private Object result;
}
