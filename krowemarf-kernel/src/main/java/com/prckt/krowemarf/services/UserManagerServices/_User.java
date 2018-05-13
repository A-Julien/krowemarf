package com.prckt.krowemarf.services.UserManagerServices;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Define the User in the framework.
 * The hash field represent the password of the user.
 * This class extends the UnicastRemoteObject, allows
 * to remotely access instances of this method
 *
 */
public interface _User extends Remote, Serializable {

    /**
     * Return the hash of the password
     * @return String of hash
     * @throws RemoteException
     */
    String getHash() throws RemoteException;

    /**
     * Return the login of the user
     * @return String login
     * @throws RemoteException
     */
    public String getLogin() throws RemoteException;

    /**
     * Compare a hash of user.
     * @param user1 user to compare
     * @param hash the hash to compare
     * @return
     * @throws RemoteException
     */
    public int compareHash(_User user1, String hash) throws RemoteException;

    /**
     * This method compare a _User by login.
     * The RMI don't allow to @Override the comparator of _User
     *
     * @param user to compare
     * @return 0 if equal
     * @throws RemoteException
     */
    public int compareUser(_User user) throws RemoteException;

}
