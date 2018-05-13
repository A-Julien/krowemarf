package com.prckt.krowemarf.services.UserManagerServices;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface _UserManager extends Remote {

    /**
     * Check if the user is allowed connected to the server
     * Verifier si le login password en paramètre correspond à l'enregistrement en BD
     *
     * @param user that must be checked
     * @return True if successful connection, False if denied
     */
    boolean connect(_User user) throws RemoteException;

    /**
     * Disconnect user be removing him into the manager
     *
     * @param user user that will be disconnected
     * @throws RemoteException
     */
    void disconnect(_User user) throws RemoteException;

    /**
     * Allows client to know all user connected
     * @return ArrayList of all user connected
     * @throws RemoteException
     */
    ArrayList<_User> getUserConnected() throws RemoteException;
}
