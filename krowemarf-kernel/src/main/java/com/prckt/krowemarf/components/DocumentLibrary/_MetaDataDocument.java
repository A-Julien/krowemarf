package com.prckt.krowemarf.components.DocumentLibrary;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface _MetaDataDocument extends Remote{
    public String getName() throws RemoteException;

    public String getExtension() throws RemoteException;

    public float getSize() throws RemoteException;

    public String getPath() throws RemoteException;

    public String getType() throws RemoteException;
}
