package com.prckt.krowemarf.components.Messenger;

import com.prckt.krowemarf.components._Component;
import com.prckt.krowemarf.components._DefaultMessage;

import java.rmi.RemoteException;

public interface _Messenger<T extends _DefaultMessage> extends _Component {
    public void subscribe(_MessengerClient user, String pseudo) throws RemoteException;
    public void unsubscribe(String pseudo) throws RemoteException;
    public void postMessage(String pseudo, T message) throws RemoteException;
}
