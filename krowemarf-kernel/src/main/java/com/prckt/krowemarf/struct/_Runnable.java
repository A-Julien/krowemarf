package com.prckt.krowemarf.struct;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public interface _Runnable {
    public abstract void run() throws RemoteException;
    public abstract void stop() throws RemoteException, NotBoundException;
}
