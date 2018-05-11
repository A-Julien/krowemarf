package com.prckt.krowemarf.components;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

public interface _DefaultMessage extends Remote, Serializable {

    public String getContent()throws RemoteException ;

    public String getSender()throws RemoteException ;

    public String toStrings() throws RemoteException;


   // £DefaultMessage clone_(_DefaultMessage m) throws RemoteException;

    public Date getDate() throws RemoteException;

    public String getDataToSave() throws RemoteException;
}
