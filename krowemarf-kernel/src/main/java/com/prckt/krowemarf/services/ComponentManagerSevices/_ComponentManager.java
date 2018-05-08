package com.prckt.krowemarf.services.ComponentManagerSevices;
import com.prckt.krowemarf.components._Component;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface _ComponentManager extends Remote {
    public _Component getComponantByName(String name) throws RemoteException;
    public String addPrivateMessenger() throws RemoteException;
}
