package com.prckt.krowemarf.components;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface _DefaultMessage extends Remote, Serializable {

    public String getContent()throws RemoteException ;

    public String getSender()throws RemoteException ;

    public String toStrings() throws RemoteException;
}
