package com.wuzx.service;

import com.wuzx.annotation.RpcService;
import com.wuzx.api.IUserService;
import com.wuzx.pojo.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wuzhixuan
 * @version 1.0.0
 * @ClassName UserServiceImpl.java
 * @Description 用户服务
 * @createTime 2022年01月13日 17:19:00
 */
@Service
@RpcService
public class UserServiceImpl implements IUserService {

    private Map<Object, User> userMap = new HashMap();

    @Override
    public User getById(int id) {
        if (userMap.size() == 0) {
            User user1 = new User(1,"张三");
            User user2 = new User(2,"李四");
            userMap.put(user1.getId(), user1);
            userMap.put(user2.getId(), user2);
        }
        return userMap.get(id);
    }
}
