package com.prckt.krowemarf.struct.server;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Info extends UnicastRemoteObject implements _Info {

    protected Info() throws RemoteException{
        super();
    };

    public String getInfo() throws RemoteException{
        System.out.println("Appel à getInfo()");
        return "Hello";
    };

}
