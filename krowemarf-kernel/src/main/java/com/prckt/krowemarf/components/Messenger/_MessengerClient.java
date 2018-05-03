package com.prckt.krowemarf.components.Messenger;

import com.prckt.krowemarf.components._DefaultMessage;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface _MessengerClient<T extends _DefaultMessage> extends Remote {
    public void displayMessage(T message) throws RemoteException;
}
