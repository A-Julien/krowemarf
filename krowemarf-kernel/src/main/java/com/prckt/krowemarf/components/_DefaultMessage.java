package com.prckt.krowemarf.components;

import com.prckt.krowemarf.services.UserManagerServices._User;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

public interface _DefaultMessage extends Remote, Serializable {

    public static final long serialVersionUID = 1L;

    public String getContent()throws RemoteException ;

    public _User getSender()throws RemoteException ;

    public String toStrings() throws RemoteException;


   // £DefaultMessage clone_(_DefaultMessage m) throws RemoteException;

    public Date getDate() throws RemoteException;

    public String getDataToSave() throws RemoteException;
}
