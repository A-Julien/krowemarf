package com.prckt.krowemarf.components.Messenger;

import com.prckt.krowemarf.components.DefaultMessage;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public abstract class MessengerClient extends UnicastRemoteObject implements _MessengerClient {

    public MessengerClient() throws RemoteException {
        super();
    }

}
