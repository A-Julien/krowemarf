package com.prckt.krowemarf.components.Messenger;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class MessengerClient extends UnicastRemoteObject implements _MessengerClient {
    private String name;

    public MessengerClient(String name) throws RemoteException {
        super();
        this.name = name;
    }

    @Override
    public void displayMessage(String message) throws RemoteException {
        System.out.println("message : " + message);
    }

    @Override
    public String getName() throws RemoteException {
        return this.name;
    }
}
