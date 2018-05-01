package com.prckt.krowemarf.struct.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface _Info extends Remote {
    public String getInfo() throws RemoteException;
}
