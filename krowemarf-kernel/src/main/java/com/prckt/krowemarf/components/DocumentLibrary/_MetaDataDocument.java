package com.prckt.krowemarf.components.DocumentLibrary;

import com.prckt.krowemarf.services.UserManagerServices.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface _MetaDataDocument extends Remote{
    public String getName() throws RemoteException;

    public String getExtension() throws RemoteException;

    public float getSize() throws RemoteException;

    public String getPath() throws RemoteException;

    public String getType() throws RemoteException;

    public static MetaDataDocument copy(_MetaDataDocument data) throws RemoteException{
        return new MetaDataDocument(data.getOwner(),data.getName(),data.getExtension(),data.getSize(),data.getPath());
    }

    public User getOwner();
}
