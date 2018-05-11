package com.prckt.krowemarf.components.Messenger;

import com.prckt.krowemarf.components._DefaultMessage;
import com.prckt.krowemarf.services.UserManagerServices._User;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface _MessengerClient<T extends _DefaultMessage> extends Remote {
    public void onReceive(T message) throws RemoteException;
    public void onLeave(_User user) throws RemoteException;
}
