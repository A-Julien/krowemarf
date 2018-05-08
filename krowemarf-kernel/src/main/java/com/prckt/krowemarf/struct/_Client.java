package com.prckt.krowemarf.struct;

import com.prckt.krowemarf.services.UserManagerServices._User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface _Client extends Remote {
    _User getUser() throws RemoteException;

    void newPrivateMessenger(ArrayList<_User> users) throws RemoteException;
}
