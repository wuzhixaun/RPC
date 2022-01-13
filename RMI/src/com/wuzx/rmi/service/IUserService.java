package com.wuzx.rmi.service;

import com.wuzx.rmi.pojo.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IUserService extends Remote {


    User getByUserId(int id) throws RemoteException;

}
