package com.prckt.krowemarf.components;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface _Component extends Remote {
    public String getName() throws RemoteException;
}
