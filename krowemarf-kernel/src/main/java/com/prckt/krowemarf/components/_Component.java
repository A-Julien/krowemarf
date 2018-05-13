package com.prckt.krowemarf.components;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

/**
 * This interface define the component
 * Each component ar identified by a unique name
 */
public interface _Component extends Remote {

    public static final String postTableName = "posts_krowemarf";
    public static final String messengerTableName = "messenger_krowemarf";
    public static final String documentLibraryTableName = "documentLibrary_krowemarf";

    /**
     * Return the name of the component
     * @return String name of the component
     * @throws RemoteException
     */
    public String getName() throws RemoteException;

    /**
     * Stop a component
     *
     * @throws SQLException
     * @throws RemoteException
     */
    public void stop() throws SQLException, RemoteException;

}
