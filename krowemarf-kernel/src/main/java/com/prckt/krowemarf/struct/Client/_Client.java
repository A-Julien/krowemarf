package com.prckt.krowemarf.struct.Client;

import com.prckt.krowemarf.services.UserManagerServices._User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface _Client extends Remote {
    /**
     * Return te user connected to the client
     *
     * @return user
     * @throws RemoteException
     */
    _User getUser() throws RemoteException;

    /**
     * Allow client to initialize a private message with one or more users
     *
     * @param users ArrayList of user
     * @throws RemoteException
     * @throws SQLException
     */
    void newPrivateMessenger(ArrayList<_User> users) throws RemoteException, SQLException;
}
