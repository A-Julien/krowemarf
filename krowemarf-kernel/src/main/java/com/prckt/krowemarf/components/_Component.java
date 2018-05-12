package com.prckt.krowemarf.components;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

public interface _Component extends Remote {

    public static final String postTableName = "posts_krowemarf";
    public static final String messengerTableName = "messenger_krowemarf_maxime";
    public static final String documentLibraryTableName = "documentLibrary_krowemarf";

    public String getName() throws RemoteException;
    public void stop() throws SQLException, RemoteException;

}
