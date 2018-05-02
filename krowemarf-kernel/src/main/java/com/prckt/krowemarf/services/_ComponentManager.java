package com.prckt.krowemarf.services;
import com.prckt.krowemarf.components.Component;
import com.prckt.krowemarf.components._Component;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface _ComponentManager extends Remote {

    public _Component getComponantByName(String name) throws RemoteException;
}
