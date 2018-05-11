package com.prckt.krowemarf.components;

import java.rmi.Remote;
import java.rmi.RemoteException;
//TODO close les connections a la bd de touts les component

public interface _Component extends Remote {
    public String getName() throws RemoteException;
    //public void setPermission(ArrayList<Object> o);
    //public ArrayList<Object> getPermission();
}
