package com.wuzx.rmi.service.impl;

import com.wuzx.rmi.pojo.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class IUserServiceImpl extends UnicastRemoteObject implements com.wuzx.rmi.service.IUserService {


    Map<Integer, User> userMap = new HashMap();
    public IUserServiceImpl() throws RemoteException {
        super();
        User user1 = new User(1, "张三");
        User user2 = new User(2, "李四");

        userMap.put(user1.getId(), user1);
        userMap.put(user2.getId(), user2);
    }

    @Override
    public User getByUserId(int id) throws RemoteException {
        return userMap.get(id);
    }
}
