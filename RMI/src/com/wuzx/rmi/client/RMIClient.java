package com.wuzx.rmi.client;

import com.wuzx.rmi.pojo.User;
import com.wuzx.rmi.service.IUserService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClient {


    public static void main(String[] args) {
        try {
            // 1、获取Registry对象
            final Registry registry = LocateRegistry.getRegistry("127.0.0.1", 9998);
            // 2、查找对应的远程对象
            final IUserService userService = (IUserService) registry.lookup("userService");

            final User user = userService.getByUserId(1);

            System.out.println(user);
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

    }
}
