package com.prckt.krowemarf.components.Messenger;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Abstract class that ensure callBack of the messenger component
 */
public abstract class £MessengerClient extends UnicastRemoteObject implements _MessengerClient {

    public £MessengerClient() throws RemoteException {
        super();
    }
}
