package com.prckt.krowemarf.services.ClientListenerManagerServices;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;



public abstract class £ClientListener extends UnicastRemoteObject implements _ClientListener{
    protected £ClientListener() throws RemoteException {
        super();
    }

    @Override
    public abstract void onNewPrivateMessenger(String composenteName) throws RemoteException;

}
