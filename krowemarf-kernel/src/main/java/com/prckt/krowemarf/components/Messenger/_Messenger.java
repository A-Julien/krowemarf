package com.prckt.krowemarf.components.Messenger;

import com.prckt.krowemarf.components._Component;

import java.rmi.RemoteException;

public interface _Messenger extends _Component {
    public void subscribe(_MessengerClient user, String pseudo) throws RemoteException;
    public void unsubscribe(String pseudo) throws RemoteException;
    public void postMessage(String pseudo, String message) throws RemoteException;
}
