package com.prckt.krowemarf.services.UserManagerServices;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface _User extends Remote {
    String getToken() throws RemoteException;

    public String getLogin() throws RemoteException;

}
