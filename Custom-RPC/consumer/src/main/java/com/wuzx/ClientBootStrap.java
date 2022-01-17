package com.wuzx;

import com.wuzx.api.IUserService;
import com.wuzx.pojo.User;
import com.wuzx.proxy.RpcClientProxy;

/**
 * @author wuzhixuan
 * @version 1.0.0
 * @ClassName ClientBootStrap.java
 * @Description TODO
 * @createTime 2022年01月17日 16:10:00
 */
public class ClientBootStrap {
    public static void main(String[] args) {
        IUserService userService = (IUserService) RpcClientProxy.createProxy(IUserService.class);
        User user = userService.getById(2);
        System.out.println(user);
    }
}
