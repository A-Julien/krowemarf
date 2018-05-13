package com.prckt.krowemarf.services.UserManagerServices;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Define the User in the framework.
 * The hash field represent the password of the user.
 * This class extends the UnicastRemoteObject, allows
 * to remotely access instances of this method
 *
 */
public class User extends UnicastRemoteObject implements _User {

    private static final long serialVersionUID = 1L;
    private final String login;
    private String hash = null;


    /**
     * First constructor that allow to create a user without password
     * @param login
     * @throws RemoteException
     */
    public User(String login) throws RemoteException {
        super();
        this.login = login;
    }

    /**
     * Second constructor of User.
     * When this constructor is called, the password pass is automatically hash
     * @param login String login of the user
     * @param password String password os the user
     * @throws RemoteException
     */
    public User(String login, String password) throws RemoteException {
        super();
        this.login = login;
        this.hash = this.hash(password);
    }

    /**
     * Return the hash of the password
     * @return String of hash
     * @throws RemoteException
     */
    @Override
    public String getHash() throws RemoteException{
        return hash;
    }

    /**
     * Return the login of the user
     * @return String login
     * @throws RemoteException
     */
    @Override
    public String getLogin() throws RemoteException {
        return login;
    }


    /**
     * This method compare a _User by login.
     * The RMI don't allow to @Override the comparator of _User
     *
     * @param user to compare
     * @return 0 if equal
     * @throws RemoteException
     */
    @Override
    public int compareUser(_User user) throws RemoteException {
        try {
            return (this.login).compareTo(user.getLogin());
        } catch (RemoteException e) {
            e.printStackTrace();
            return Integer.parseInt(null);
        }
    }

    /**
     * Redefine the equal for this classe
     *
     * @param o the object to compare
     * @return True if equals, False if not equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return login.equals(((User) o).login);
    }

    /**
     * Algorithm how hash the password
     * @param str the password that will be hash
     * @return the hashed String
     */
    private String hash(String str) throws RemoteException{
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(str.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            str = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

        return str;
    }

    /**
     * Compare a hash of user.
     * @param user1 user to compare
     * @param hash the hash to compare
     * @return
     * @throws RemoteException
     */
    @Override
    public int compareHash(_User user1, String hash) throws RemoteException {
        if(user1.getHash().equals(hash)) return 0;
        return 1;
    }
}
