package com.prckt.krowemarf.services.UserManagerServices;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User extends UnicastRemoteObject implements _User {
    private static final long serialVersionUID = 1L;
    private String login;
    private String hash = null;

    public User(String login) throws RemoteException {
        super();
        this.login = login;
    }

    public User(String login, String password) throws RemoteException {
        this.login = login;
        this.hash = this.hash(password);
    }

    @Override
    public String getHash() throws RemoteException{
        return hash;
    }

    @Override
    public String getLogin() throws RemoteException {
        return login;
    }


    @Override
    public int compareUser(_User user) throws RemoteException {
        try {
            return (this.login).compareTo(user.getLogin());
        } catch (RemoteException e) {
            e.printStackTrace();
            return Integer.parseInt(null);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return login.equals(((User) o).login);
    }

    /**
     * hash un String
     * @param str
     * @return
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

    @Override
    public int compareHash(_User user1, String hash) throws RemoteException {
        if(user1.getHash().equals(hash)) return 0;
        return Integer.parseInt(null);
    }
}
