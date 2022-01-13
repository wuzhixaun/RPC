package com.wuzx.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wuzhixuan
 * @version 1.0.0
 * @ClassName RpcService.java
 * @Description 对外暴露服务接口
 * @createTime 2022年01月13日 17:17:00
 */
@Target(ElementType.TYPE) // 用户类或者接口上面
@Retention(RetentionPolicy.RUNTIME) // 生命周期是运行时
public @interface RpcService {

}
