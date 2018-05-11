package com.prckt.krowemarf.components;

import java.rmi.Remote;
import java.rmi.RemoteException;
//TODO close les connections a la bd de touts les component

public interface _Component extends Remote {

    public static final String postTableName = "posts_krowemarf";
    public static final String messengerTableName = "messenger_krowemarf";
    public static final String documentLibraryTableName = "documentLibrary_krowemarf";

    public String getName() throws RemoteException;

}
