package com.prckt.krowemarf.struct;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public interface _Runnable {
    public abstract void run() throws RemoteException;
    public abstract void stop() throws RemoteException, NotBoundException;
    final String componentManagerName = "componentManager";

    default String buildRmiAddr(String name, String adresse){
        return "rmi://" + adresse + "/"+ name;
    }
}
