package com.prckt.krowemarf.components.Messenger;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public abstract class £MessengerClient extends UnicastRemoteObject implements _MessengerClient {

    public static final String s = "sqd";

    public £MessengerClient() throws RemoteException {
        super();
    }
}
