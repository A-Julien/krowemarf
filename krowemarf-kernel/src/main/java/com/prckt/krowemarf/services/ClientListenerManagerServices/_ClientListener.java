package com.prckt.krowemarf.services.ClientListenerManagerServices;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface  _ClientListener extends Remote{
    public void onNewPrivateMessenger(String composenteName) throws RemoteException;
}
