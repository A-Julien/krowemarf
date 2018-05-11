package com.prckt.krowemarf.services.ClientListenerManagerServices;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

public interface  _ClientListener extends Remote{
    public void onNewPrivateMessenger(String composenteName) throws RemoteException, SQLException;
}
