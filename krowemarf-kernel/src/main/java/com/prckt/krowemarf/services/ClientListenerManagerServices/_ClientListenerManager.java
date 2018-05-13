package com.prckt.krowemarf.services.ClientListenerManagerServices;

import com.prckt.krowemarf.services.UserManagerServices._User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Initialize the clientListener
 */
public interface _ClientListenerManager extends Remote{

    /**
     * Allows client to initialize the client listener.
     *
     * @param user the user how add the listener
     * @param clientListener the listener of the client
     * @throws RemoteException
     */
    public void addListener(_User user, _ClientListener clientListener) throws RemoteException;

    /**
     * Allows client to initialize private messenger
     *
     * @param idComponent the component id
     * @param usersTargets the list of the targeted user for the private message
     * @throws RemoteException
     * @throws SQLException
     */
    public void initMp(String idComponent, ArrayList<_User> usersTargets) throws RemoteException, SQLException;


    /**
     * Remove a client listner in the list
     *
     * @param user the user that that will be remove
     * @throws RemoteException
     */
    public void removeListner(_User user) throws RemoteException;
}
