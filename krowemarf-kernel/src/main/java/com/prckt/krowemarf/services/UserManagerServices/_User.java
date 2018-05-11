package com.prckt.krowemarf.services.UserManagerServices;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface _User extends Remote, Serializable {
    String getHash() throws RemoteException;

    public String getLogin() throws RemoteException;

    public int compareHash(_User user1, String hash) throws RemoteException;

    public int compareUser(_User user) throws RemoteException;
}
